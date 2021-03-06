/*
 * @(#)lmstreetoc.js  3.4.1  2010-02-10
 *
 * Copyright (c) 2003-2010 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */ 
/**
 * This scripts inserts a table of contents (TOC) at the current location
 * into a HTML document.
 *
 * This file is intended to be included in the body of a HTML document. The
 * HTML document must be in a child frame of the Learning Management System
 * generated by TinyLMS.
 *
 * Example:
 * <html>
 *   <head>
 *     <title>Table Of Contents</title>
 *     <script language="JavaScript" src="lib/collections.js" type="text/JavaScript"></script>
 *     <script language="JavaScript" src="lib/htmltree.js" type="text/JavaScript"></script>
 *     <script language="JavaScript" src="lib/lmsstub.js" type="text/JavaScript"></script>
 *   </head>
 *   <body>
 *     <script language="JavaScript" src="lib/lmstreetoc.js" type="text/JavaScript"></script>
 *   </body>
 * </html>
 *
 * @version
 * 3.4.1 2010-02-10 IE7 and IE8 need the same workarounds as IE6 - sigh.
 * 3.4 2009-09-30 Move Quiz-item out of the TOC-tree into the tree buttons.
 * 3.3.1 2009-09-30 a-element in treetoc was not closed.
 * 3.3 2008-12-03 Don't display "Expand All"/"Collapse All" buttons,
 * if the tree has only a depth of 1.
 * <br>3.2 2008-03-17 Fixed layout issues with IE6.
 * 3.1 2007-08-09 Added a button to the index page to the layout.
 * 3.0 2007-06-18 Redesigned to create better stylable HTML code.
 * 2.3.1 2006-09-06 Disable DIV overflow feature when running on MS IE. 
 * 2.3 2006-07-29 Use a DIV with overflow scrolling for the TOC.
 * 2.2 2006-07-11 Added support for navigation buttons (again). 
 * 2.1 2006-05-26 Display status only, if the property 
 * isStatusInTOCVisible is set to true in the API object.
 * 2.0 2006-05-17 Removed navigation buttons from TOC. The TOC design
 * relies now more on CSS stylesheets than it did before.
 * 1.3 2004-06-20 Expanding and collapsing nodes did not work on some browsers.
 *                         Show a disabled disclosure symbol for nodes that can not be collapsed by the user.
 * 1.2.1 2004-06-14 We must not show an item as emphasized, when TinyLMS is not in course mode.
 * 1.2 2004-06-13 Support for debugging improved. 
 * 1.1.7 2003-11-06 Support for debugging added.
 * 1.1 2003-11-04 Adapted to changes in lmsapi.js and lmscam.js
 * 1.0 2003-09-12 Locale specific labels are now read from the API object.
 * 0.20.1 2003-04-07 Safari browser can not handle return statements
 * within switch statements.
 * 0.19.4 2003-04-04 Revised.
 * 0.19 2003-03-26 Revised.
 * 0.18 2003-03-26 Revised.
 * 0.17 2003-03-16 Naming conventions for CAM elements streamlined with Java implementation.
 * 0.15.2 2003-03-11 Revised.
 * 0.3.1 2003-03-11 Revised.
 * 0.3 2003-03-10 Colors adapted to new color scheme.
 * 0.2 2003-02-27 Revised.
 * 0.1 2003-02-05 Created.
 */


/**
 * Writes the title of the TOC.
 *
 * @param organizationElement the CAM organization element
 * @return void
 */
TOC.prototype.writeTitle = function(organizationElement) {
	document.writeln('<div id="tocTitle">');
	document.writeln('<a href="#" onclick="stub.getAPI().gotoIndex()">'+organizationElement.title+'</a>'); 
	document.writeln('</div>');
}

/**
 * Writes the tree buttons.
 *
 * @return void
 */
TOC.prototype.writeTreeButtons = function(currentItem) {
    var api = stub.getAPI();
    var depth = api.getCurrentOrganization().getDepth();
	
	document.write('<div id="tocTreeButtons">');
	
	// BEGIN PATCH Multilingual Course
	document.write('<a href="../index_en.html" target="_top" title="English"><img src="famfamfam_flag_icons/png/gb.png"></a>');
	document.write(' ');
	document.write('<a href="../index_de.html" target="_top" title="Deutsch"><img src="famfamfam_flag_icons/png/de.png"></a>');
	document.write(' | ');
	// END PATCH Multilingual Course
	
	document.write('<a href="#" onclick="stub.getAPI().gotoIndex()">');
	document.write(api.labels.get('toc.index'));
	document.write('</a>');

    if (api.isQuiz) {
        document.write(' | ');

        var node, title;
        if (api.getCurrentOrganization() == api.cam.organizations.getChildAt(0)) {
            // Write "Quizmode item" commands if the current organisation is 0.
            node = api.cam.organizations.getChildAt(1).getChildAt(0);
            title = api.labels.get("quiz.quizmode.title");
        } else {
            // Write "Lessonmode item" commands if the current organisation is != 0.
            // Write "Quizmode item" commands if the current organisation is 0.
            node = api.cam.organizations.getChildAt(0).getChildAt(0);
            title = api.labels.get("quiz.coursemode.title");
        }
        var lessonStatus = "";
        if (node == currentItem || !node.isTraversable() && node.isNodeDescendant(currentItem)) {
            lessonStatus = "current";
        }
        var statusClass = this.getCSSStatusClass(lessonStatus);
        document.write('<a href="#" ');
        document.write('class="'+statusClass+'" ');
        document.write('onclick="stub.getAPI().gotoItemWithID(\''+node.identifier+'\')">');
        document.write(title);
        document.write('</a>');
    }
    if (depth > 1 && false) {
        // Write "Expand All" | "Collapse All" commands. 
        // Note: this is currently deactivated.
        document.write(' | ');
        document.write('<a href="#" onclick="toc.htmltree.expandAll();">');
        document.write(api.labels.get('toc.expandAll'));
        document.write('</a>');
        document.write(' | ');
        document.write('<a href="#" onclick="toc.htmltree.collapseAll();">');
        document.write(api.labels.get('toc.collapseAll'));
        document.write('</a>');
    }
	if (api.showDebugButtons) {
		document.writeln('<br/>');
		document.write('<a href="#" onclick="stub.getAPI().toggleLogging();stub.getAPI().fireUpdateTOC();">');
		if (api.logger.level > 0) {
			document.write(api.labels.get('debug.switchLoggingOff'));
		} else {
			document.write(api.labels.get('debug.switchLoggingOn'));
		}
		document.writeln('</a>');
	}
	
	if (api.showBugInfoButton) {
		if (api.showDebugButtons) {
			document.write(' | ');
		} else {
			document.writeln('<br/>');
		}			
		document.write('<a href="#" onclick="stub.getAPI().showBugInfo()">');
		document.write(api.labels.get('debug.showInfo'));
		document.write('</a>');
	}


	document.writeln('</div>');
}
/**
 * Writes the navigation buttons.
 *
 * @return void
 */
TOC.prototype.writeNavigationButtons = function() {
    var api = stub.getAPI();
	if (api.isTreeButtonsVisible) {
		document.write('<div id="tocNavButtons">');
		document.write('<a href="#" onclick="stub.getAPI().gotoPreviousItem()">'+api.labels.get('toc.previous')+'</a>');
		document.write('<br>');
		document.write('<a href="#" onclick="stub.getAPI().gotoNextItem()">'+api.labels.get('toc.next')+'</a>');
		document.write('</div>');
	}
}

/**
 * Returns the CSS class for the specified lesson status.
 *
 * @param lessonStatus The lesson status of a CAM SCO resource element.
 * @return The CSS class name (a String).
 */
TOC.prototype.getCSSStatusClass = function(lessonStatus) {
    var api = stub.getAPI();
    var result = "tocStatus";

    if (api.isStatusInTOCVisible) {
	switch (lessonStatus) {
		case "passed" :
		case "completed" :
			result += "Passed";
			break;
		case "failed" :
			result += "Failed";
			break;
		case "incomplete" :
		case "browsed" :
			result += "Browsed";
			break;
		case "not attempted" :
		case "" :
			result += "NotAttempted";
			break;
		case "current" :
			result += "Current";
			break;
		default :
			result = "";
			break;
	}
    } else {
	switch (lessonStatus) {
		case "passed" :
		case "completed" :
		case "failed" :
		case "incomplete" :
		case "browsed" :
		case "not attempted" :
		case "" :
			result += "NotAttempted";
			break;
		case "current" :
			result += "Current";
			break;
		default :
			result = "";
			break;
	}
    }
	return result;
	
}

/**
 * Writes the TOC tree.
 *
 * @param parent The parent node of the tree. Must be an instance of lmscam.js:ItemElement
 * @param currentItem The selected item. currentItem must be an instance of lmscam.js:ItemElement
 * @param depth The number of parent nodes
 */
TOC.prototype.writeTree = function(parent, currentItem, depth) {
	document.write('<ul>');
	for (var i=0; i < parent.getChildCount(); i++) {
		var node = parent.getChildAt(i);
		var resource = node.getResource();
		var hasResource = resource != null;
		var lessonStatus = (hasResource) ? resource.cmi_core_lesson_status : "";
		if (node == currentItem || !node.isTraversable() && node.isNodeDescendant(currentItem)) {
                    lessonStatus = "current";
                }
		var statusClass = this.getCSSStatusClass(lessonStatus);
		if (!hasResource && node.isTraversable()) {
                    statusClass = "tocStatusNoSCO";
		}
		
		document.write('<li id="tocitem_'+node.identifier+'"');
		if (node.isExpanded || node.isNodeDescendant(currentItem)) {
			document.write(' class="tocNodeExpanded"');
		}
		document.write('>');
 		if (resource != null || node.getChildCount() > 0) {
                    document.write('<a href="#" ');
                    document.write('class="'+statusClass+'" ');
                    document.write('onclick="stub.getAPI().gotoItemWithID(\''+node.identifier+'\')">');
                    document.write(node.title);
                    document.write('</a>');
		} else {
                    document.write(node.title);
		}
		if (node.getChildCount() > 0 && node.isTraversable()) {
                    this.writeTree(node, currentItem);
		}
		document.writeln('</li>');
	}
	document.write('</ul>');
}

/**
 * Writes the table of contents as a tree including 
 * - the title of the CAM organization
 * - buttons for expanding and collapsing tree nodes
 * - the TOC tree
 * - navigation buttons (index, next/previous SCO, logout)
 */
TOC.prototype.writeTOC = function() {
    var api = stub.getAPI();
    if (api.isLoggedIn()) {
        var currentItem = null;
        if (api.mode == api.MODE_COURSE) {
            currentItem = api.getAnticipatedItem();
            if (currentItem == null) currentItem = api.getCurrentItem();
        }

        document.write('<div id="tocHeader">');
        this.writeTitle(api.cam.organizations.getChildAt(0));
        //this.writeTitle(api.getCurrentOrganization());
        this.writeTreeButtons(currentItem);
        document.write('</div>');

        var tocTop = (api.showDebugButtons || api.showBugInfoButton) ? 126 : 90;
        var tocBottom = (api.isTreeButtonsVisible) ? 36 : 0;
        var isIE = navigator.appVersion.indexOf('MSIE 6') != -1
                    || navigator.appVersion.indexOf('MSIE 7') != -1
                    || navigator.appVersion.indexOf('MSIE 8') != -1;

        if (isIE) {
            var tocHeight = api.tocHeight - tocTop - tocBottom;
            var tocWidth = api.tocWidth;
            document.write('<div id="tocTree" style="top:'+tocTop+'px;bottom:'+tocBottom+'px;width:'+tocWidth+'px;height:'+tocHeight+'px;">');
        } else {
            document.write('<div id="tocTree" style="top:'+tocTop+"px;bottom:"+tocBottom+'px;">');
        }
        this.writeTree(api.getCurrentOrganization(), currentItem);
        document.write('</div>');

        document.write('<div id="tocFooter">');
        this.writeNavigationButtons();
        document.write('</div>');

        this.htmltree = new HTMLTree();
        this.htmltree.treediv = document.getElementById("tocTree");
        if (this.htmltree.treediv != null) {
            this.htmltree.expandedClass = "tocNodeExpanded";
            this.htmltree.collapsedClass = "tocNodeCollapsed";
            this.htmltree.leafClass = "tocNodeLeaf";
            this.htmltree.expandedImage = "style/tocNodeExpanded.gif";
            this.htmltree.collapsedImage = "style/tocNodeCollapsed.gif";
            this.htmltree.leafImage = "style/tocNodeLeaf.gif";
            var self = this;
            this.htmltree.elementExpanded = function(li) { self.elementExpanded(li); };
            this.htmltree.elementCollapsed = function(li) { self.elementCollapsed(li); };
            this.htmltree.init();
        }
    }
}

/**
 * This function is called when a list item is expanded.
 * @param aLi The <LI> HTML element which was expanded. 
 */
TOC.prototype.elementExpanded = function(aLi) {
    var id = aLi.id.substring(8); // cut off "subitem_" from the id.
    var api = stub.getAPI();
    var node = api.cam.organizations.findByIdentifier(id);
    node.isExpanded = true;
}

/**
 * This function is called when a list item is collapsed.
 * @param aLi The <LI> HTML element which was collapsed. 
 */
TOC.prototype.elementCollapsed = function(aLi) {
    var id = aLi.id.substring(8); // cut off "subitem_" from the id.
    var api = stub.getAPI();
    var node = api.cam.organizations.findByIdentifier(id);
    node.isExpanded = false;
}

function TOC() {
	this.htmltree = null; // initialized by writeTree
}
var toc = new TOC();
toc.writeTOC();
