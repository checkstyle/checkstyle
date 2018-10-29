package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.Date; //indent:0 exp:0

@Entity //indent:0 exp:0
@SecondaryTable(name = "TV_PROGRAM_EXT", pkJoinColumns = { //indent:0 exp:0
    @PrimaryKeyJoinColumn(name = "CHANNEL_ID"), //indent:4 exp:4
    @PrimaryKeyJoinColumn(name = "PRESENTER_NAME") //indent:4 exp:4
        }) //indent:8 exp:0 warn
@AssociationOverrides( { //indent:0 exp:0
    @AssociationOverride(name = "id.channel"), //indent:4 exp:4
    @AssociationOverride(name = "id.presenter")}) //indent:4 exp:4
public class InputIndentationAnnotationScopeIndentationCheck { //indent:0 exp:0
    @EmbeddedId //indent:4 exp:4
    public String id; //indent:4 exp:4

    @Temporal(TemporalType.TIME) //indent:4 exp:4
    Date time; //indent:4 exp:4

    @Column(name = "TXT", table = "TV_PROGRAM_EXT") //indent:4 exp:4
    public String text; //indent:4 exp:4

} //indent:0 exp:0

@interface Entity {} //indent:0 exp:0
@interface SecondaryTable { //indent:0 exp:0
    String name(); //indent:4 exp:4
    PrimaryKeyJoinColumn[] pkJoinColumns(); //indent:4 exp:4
} //indent:0 exp:0
@interface AssociationOverrides { //indent:0 exp:0
    AssociationOverride[] value(); //indent:4 exp:4
} //indent:0 exp:0
@interface EmbeddedId {} //indent:0 exp:0
@interface Temporal { //indent:0 exp:0
    String value(); //indent:4 exp:4
} //indent:0 exp:0
@interface Column { //indent:0 exp:0
    String name(); //indent:4 exp:4
    String table(); //indent:4 exp:4
} //indent:0 exp:0
@interface PrimaryKeyJoinColumn { //indent:0 exp:0
    String name(); //indent:4 exp:4
} //indent:0 exp:0
@interface AssociationOverride { //indent:0 exp:0
    String name(); //indent:4 exp:4
    JoinColumn joinColumns() default @JoinColumn(name = "prese", nullable = false); //indent:4 exp:4
} //indent:0 exp:0
@interface JoinColumn { //indent:0 exp:0
    String name(); //indent:4 exp:4
    boolean nullable(); //indent:4 exp:4
} //indent:0 exp:0
class TemporalType { //indent:0 exp:0
    static final String TIME = ""; //indent:4 exp:4
} //indent:0 exp:0
