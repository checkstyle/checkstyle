TocTypeEnum = {
  VERTICAL: 1,
  HORIZONTAL: 2
};

function CreateTOC(tocElement) {

  var toc = document.getElementById(tocElement);
  if (!toc) return;

  var tocTypeClass = toc.className;
  var tocType;

  switch (tocTypeClass) {
    case 'horizontal_toc':
      tocType = TocTypeEnum.HORIZONTAL;
      break;
    case 'vertical_toc':
      tocType = TocTypeEnum.VERTICAL;
      break;
    default:
      tocType = TocTypeEnum.VERTICAL;
      break;
  }

  var headingLevels = 'h2,h3';
  var headings = document.querySelectorAll(headingLevels);

  var tocHeadingDiv = document.createElement('div');
  toc.appendChild(tocHeadingDiv);
  tocHeadingDiv.className = 'toc_title';
  var tocHeading = document.createElement('h3');
  toc.appendChild(tocHeading);
  tocHeading.className = 'ignoreLink';
  tocHeading.id = 'toc';
  var tocText = document.createTextNode('Table of Contents');
  tocHeading.appendChild(tocText);

  var tocTable = document.createElement('table');
  if (tocType == TocTypeEnum.VERTICAL) {
    tocTable.className = 'columns';
  }
  toc.appendChild(tocTable);

  var tbody_element = document.createElement('tbody');
  tbody_element.setAttribute('valign', 'top');
  tbody_element.className = 'toc';
  tocTable.appendChild(tbody_element);

  var masterLevel = parseInt(headingLevels.charAt(1));
  var lowestLevel = parseInt(headingLevels.charAt(headingLevels.length - 1));

  switch (tocType) {
    case TocTypeEnum.HORIZONTAL:
      CreateHorizontalTOC(headings, masterLevel, lowestLevel, tbody_element);
      break;
    case TocTypeEnum.VERTICAL:
      CreateVerticalTOC(headings, masterLevel, lowestLevel, tbody_element);
      break;
    default:
  }
}

function CreateHorizontalTOC(headings, masterLevel, lowestLevel, tbody_element) {
  var h = 0;
  var ignoreChildren = false;
  var toc_current_row;

  while (h < headings.length) {
    var heading = headings[h];
    var level = parseInt(heading.tagName.charAt(1));

    if (isNaN(level) || level < 1 || level > lowestLevel) {
      h++;
      continue;
    }

    if ((level == masterLevel) && (!hasClass(heading, 'ignoreLink'))) {
      toc_current_row = AddTOCMaster(tbody_element, heading);
      ignoreChildren = false;
    }

    if ((level == masterLevel) && (hasClass(heading, 'ignoreLink'))) {
      ignoreChildren = true;
    }

    if ((level != masterLevel) && (!ignoreChildren) && toc_current_row) {
      AddTOCElements(toc_current_row, heading);
    }

    h++;
  }
}

function AddTOCMaster(tocTable, heading) {
  var toc_tr = document.createElement('tr');
  tocTable.appendChild(toc_tr);
  toc_tr.setAttribute('valign', 'top');
  var toc_tr_td = document.createElement('td');
  toc_tr.appendChild(toc_tr_td);
  var toc_category = document.createElement('div');
  toc_tr_td.appendChild(toc_category);
  toc_category.className = 'toc_category';

  var link = document.createElement('a');
  link.href = '#' + heading.id;
  link.textContent = heading.textContent;
  toc_category.appendChild(link);

  var toc_td = document.createElement('td');
  toc_tr.appendChild(toc_td);
  var toc_td_div = document.createElement('div');
  toc_td_div.className = 'toc_stylepoint';
  toc_td.appendChild(toc_td_div);

  return (toc_td_div);
}

function AddTOCElements(toc_div, heading) {
  if (heading.offsetParent === null) {
    return;
  }
  var toc_list_element = document.createElement('li');
  toc_list_element.className = 'toc_entry';
  toc_div.appendChild(toc_list_element);

  var link = document.createElement('a');
  link.href = '#' + heading.id;
  link.textContent = heading.textContent;
  toc_list_element.appendChild(link);
}

function CreateVerticalTOC(headings, masterLevel, lowestLevel, tbody_element) {
  var toc_tr = document.createElement('tr');
  tbody_element.appendChild(toc_tr);
  var toc_tr_td = document.createElement('td');
  toc_tr_td.className = 'two_columns';
  toc_tr.appendChild(toc_tr_td);

  var h = 0;
  var toc_current_col = null;
  var ignoreChildren = false;

  while (h < headings.length) {
    var heading = headings[h];
    var level = parseInt(heading.tagName.charAt(1));

    if (isNaN(level) || level < 1 || level > lowestLevel) {
      h++;
      continue;
    }

    if ((level == masterLevel) && (!hasClass(heading, 'ignoreLink'))) {
      if (heading.offsetParent !== null) {
        var td_dl = document.createElement('dl');
        toc_tr_td.appendChild(td_dl);
        var td_dt = document.createElement('dt');
        td_dl.appendChild(td_dt);
        toc_current_col = td_dl;

        var link = document.createElement('a');
        link.href = '#' + heading.id;
        link.textContent = heading.textContent;
        td_dt.appendChild(link);
        ignoreChildren = false;
      }
    }

    if ((level == masterLevel) && (hasClass(heading, 'ignoreLink'))) {
      ignoreChildren = true;
    }

    if ((level != masterLevel) && (!ignoreChildren) && toc_current_col) {
      if (heading.offsetParent !== null) {
        var td_dd = document.createElement('dd');
        toc_current_col.appendChild(td_dd);
        var link = document.createElement('a');
        link.href = '#' + heading.id;
        link.textContent = heading.textContent;
        td_dd.appendChild(link);
      }
    }

    h++;
  }
}

function hasClass(element, cls) {
  return (' ' + element.className + ' ').indexOf(' ' + cls + ' ') > -1;
}

function LinkifyHeader(header, fileName, sizePixels) {
  var link = document.createElement('a');
  link.href = '#' + header.id;
  link.setAttribute('alt', 'link to ' + header.id);
  link.innerHTML =
    '<img src="include/' + fileName + '"' +
    ' width=' + sizePixels +
    ' height=' + sizePixels +
    ' style="float:left;position:relative;bottom:5px;">';
  header.appendChild(link);
}

function LinkifyHeadersForTag(tagName) {
  var headers = document.getElementsByTagName(tagName);
  var header;
  for (var j = 0; j != headers.length; j++) {
    header = headers[j];
    if (!hasClass(header, 'ignoreLink') && ('id' in header)) {
      if (header.id != '') {
        LinkifyHeader(header, 'link.png', 21);
        header.style.left = '-46px';
        header.style.position = 'relative';
      }
    }
  }
}

function LinkifyHeaders() {
  LinkifyHeadersForTag('h2');
  LinkifyHeadersForTag('h3');
  LinkifyHeadersForTag('h4');
}

function initStyleGuide() {
  LinkifyHeaders();
  CreateTOC('tocDiv');
}
