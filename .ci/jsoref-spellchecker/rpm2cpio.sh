#!/bin/sh

pkg=$1
if [ "$pkg" = "" -o ! -e "$pkg" ]; then
    echo "no package supplied" 1>&2
    exit 1
fi

leadsize=96
o=`expr $leadsize + 8`
set `od -j $o -N 8 -t u1 $pkg`
il=`expr 256 \* \( 256 \* \( 256 \* $2 + $3 \) + $4 \) + $5`
dl=`expr 256 \* \( 256 \* \( 256 \* $6 + $7 \) + $8 \) + $9`
# echo "sig il: $il dl: $dl"

sigsize=`expr 8 + 16 \* $il + $dl`
o=`expr $o + $sigsize + \( 8 - \( $sigsize \% 8 \) \) \% 8 + 8`
set `od -j $o -N 8 -t u1 $pkg`
il=`expr 256 \* \( 256 \* \( 256 \* $2 + $3 \) + $4 \) + $5`
dl=`expr 256 \* \( 256 \* \( 256 \* $6 + $7 \) + $8 \) + $9`
# echo "hdr il: $il dl: $dl"

hdrsize=`expr 8 + 16 \* $il + $dl`
o=`expr $o + $hdrsize`
EXTRACTOR="dd if=$pkg ibs=$o skip=1"

COMPRESSION=`($EXTRACTOR |file -) 2>/dev/null`
if echo $COMPRESSION |grep -q gzip; then
        DECOMPRESSOR=gunzip
elif echo $COMPRESSION |grep -q bzip2; then
        DECOMPRESSOR=bunzip2
elif echo $COMPRESSION |grep -iq xz; then # xz and XZ safe
        DECOMPRESSOR=unxz
elif echo $COMPRESSION |grep -q cpio; then
        DECOMPRESSOR=cat
else
        # Most versions of file don't support LZMA, therefore we assume
        # anything not detected is LZMA
        DECOMPRESSOR=`which unlzma 2>/dev/null`
        case "$DECOMPRESSOR" in
            /* ) ;;
            *  ) DECOMPRESSOR=`which lzmash 2>/dev/null`
             case "$DECOMPRESSOR" in
                     /* ) DECOMPRESSOR="lzmash -d -c" ;;
                     *  ) DECOMPRESSOR=cat ;;
                 esac
                 ;;
        esac
fi

$EXTRACTOR 2>/dev/null | $DECOMPRESSOR
