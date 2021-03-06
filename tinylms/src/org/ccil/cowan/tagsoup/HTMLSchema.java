// This file is part of TagSoup.
// 
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.  You may also distribute
// and/or modify it under version 3.0 of the Academic Free License.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
// 
// 
/**
This class provides a Schema that has been preinitialized with HTML
elements, attributes, and character entity declarations.  All the declarations
normally provided with HTML 4.01 are given, plus some that are IE-specific
and NS4-specific.  Attribute declarations of type CDATA with no default
value are not included.
*/

package org.ccil.cowan.tagsoup;
public class HTMLSchema extends Schema implements HTMLModels {

	/**
	Returns a newly constructed HTMLSchema object independent of
	any existing ones.
	*/

	public HTMLSchema() {
		// Start of Schema calls
				setURI("http://www.w3.org/1999/xhtml");
		setPrefix("html");
		elementType("<pcdata>", M_EMPTY, M_PCDATA, 0);
		elementType("<root>", M_ROOT, M_EMPTY, 0);
		elementType("a", M_PCDATA|M_NOLINK, M_INLINE, 0);
		elementType("abbr", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("acronym", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("address", M_PCDATA|M_INLINE|M_P, M_BLOCK, 0);
		elementType("applet", M_PCDATA|M_PARAM|M_INLINE|M_BLOCK, M_INLINE|M_NOLINK, 0);
		elementType("area", M_EMPTY, M_AREA, 0);
		elementType("b", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("base", M_EMPTY, M_HEAD, 0);
		elementType("basefont", M_EMPTY, M_INLINE|M_NOLINK, 0);
		elementType("bdo", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("big", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("blockquote", M_PCDATA|M_INLINE|M_BLOCK, M_BLOCK, 0);
		elementType("body", M_PCDATA|M_INLINE|M_BLOCK, M_HTML|M_BODY, 0);
		elementType("br", M_EMPTY, M_INLINE|M_NOLINK, 0);
		elementType("button", M_PCDATA|M_INLINE|M_BLOCK, M_INLINE|M_NOLINK, 0);
		elementType("caption", M_PCDATA|M_INLINE, M_TABLE, 0);
		elementType("center", M_PCDATA|M_INLINE|M_BLOCK, M_BLOCK, 0);
		elementType("cite", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("code", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("col", M_EMPTY, M_COL|M_TABLE, 0);
		elementType("colgroup", M_COL, M_TABLE, 0);
		elementType("dd", M_PCDATA|M_INLINE|M_BLOCK, M_DEF, 0);
		elementType("del", M_PCDATA|M_INLINE|M_BLOCK, M_INLINE|M_BLOCKINLINE|M_BLOCK, F_RESTART);
		elementType("dfn", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("dir", M_LI, M_BLOCK, 0);
		elementType("div", M_PCDATA|M_INLINE|M_BLOCK, M_BLOCK, 0);
		elementType("dl", M_DEF, M_BLOCK, 0);
		elementType("dt", M_PCDATA|M_INLINE, M_DEF, 0);
		elementType("em", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("fieldset", M_PCDATA|M_LEGEND|M_INLINE|M_BLOCK, M_BLOCK, 0);
		elementType("font", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, 0);
		elementType("form", M_PCDATA|M_INLINE|M_NOLINK|M_BLOCK|M_TR|M_CELL, M_BLOCK|M_FORM, F_NOFORCE);
		elementType("frame", M_EMPTY, M_FRAME, 0);
		elementType("frameset", M_FRAME, M_FRAME|M_HTML, 0);
		elementType("h1", M_PCDATA|M_INLINE, M_BLOCK, 0);
		elementType("h2", M_PCDATA|M_INLINE, M_BLOCK, 0);
		elementType("h3", M_PCDATA|M_INLINE, M_BLOCK, 0);
		elementType("h4", M_PCDATA|M_INLINE, M_BLOCK, 0);
		elementType("h5", M_PCDATA|M_INLINE, M_BLOCK, 0);
		elementType("h6", M_PCDATA|M_INLINE, M_BLOCK, 0);
		elementType("head", M_HEAD, M_HTML, 0);
		elementType("hr", M_EMPTY, M_BLOCK, 0);
		elementType("html", M_HTML, M_ROOT, 0);
		elementType("i", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("iframe", M_PCDATA|M_INLINE|M_BLOCK, M_INLINE|M_NOLINK, 0);
		elementType("img", M_EMPTY, M_INLINE|M_NOLINK, 0);
		elementType("input", M_EMPTY, M_INLINE|M_NOLINK, 0);
		elementType("ins", M_PCDATA|M_INLINE|M_BLOCK, M_INLINE|M_BLOCK, F_RESTART);
		elementType("isindex", M_EMPTY, M_HEAD, 0);
		elementType("kbd", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("label", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, 0);
		elementType("legend", M_PCDATA|M_INLINE, M_LEGEND, 0);
		elementType("li", M_PCDATA|M_INLINE|M_BLOCK, M_LI, 0);
		elementType("link", M_EMPTY, M_HEAD, 0);
		elementType("map", M_BLOCK|M_AREA, M_INLINE, 0);
		elementType("menu", M_LI, M_BLOCK, 0);
		elementType("meta", M_EMPTY, M_HEAD, 0);
		elementType("noframes", M_BODY|M_BLOCK|M_INLINE, M_BLOCK|M_HTML|M_FRAME, 0);
		elementType("noscript", M_PCDATA|M_INLINE|M_BLOCK, M_BLOCK, 0);
		elementType("object", M_PCDATA|M_PARAM|M_INLINE|M_BLOCK, M_HEAD|M_INLINE|M_NOLINK, 0);
		elementType("ol", M_LI, M_BLOCK, 0);
		elementType("optgroup", M_OPTIONS, M_OPTIONS, 0);
		elementType("option", M_PCDATA, M_OPTION|M_OPTIONS, 0);
		elementType("p", M_PCDATA|M_INLINE, M_BLOCK|M_P, 0);
		elementType("param", M_EMPTY, M_PARAM, 0);
		elementType("pre", M_PCDATA|M_INLINE, M_BLOCK, 0);
		elementType("q", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("s", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("samp", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("script", M_PCDATA, M_ANY & ~M_ROOT, F_CDATA);
		elementType("select", M_OPTIONS, M_INLINE, 0);
		elementType("small", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("span", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("strike", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("strong", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("style", M_PCDATA, M_HEAD|M_INLINE, F_CDATA);
		elementType("sub", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("sup", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("table", M_FORM|M_TABLE, M_BLOCK, F_NOFORCE);
		elementType("tbody", M_TR, M_TABLE, 0);
		elementType("td", M_PCDATA|M_INLINE|M_BLOCK, M_CELL, 0);
		elementType("textarea", M_PCDATA, M_INLINE, 0);
		elementType("tfoot", M_TR, M_TABLE, 0);
		elementType("th", M_PCDATA|M_INLINE|M_BLOCK, M_CELL, 0);
		elementType("thead", M_TR, M_TABLE, 0);
		elementType("title", M_PCDATA, M_HEAD, 0);
		elementType("tr", M_FORM|M_CELL, M_TR|M_TABLE, 0);
		elementType("tt", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("u", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, F_RESTART);
		elementType("ul", M_LI, M_BLOCK, 0);
		elementType("var", M_PCDATA|M_INLINE, M_INLINE|M_NOLINK, 0);
		parent("<pcdata>", "body");
		parent("html", "<root>");
		parent("a", "body");
		parent("abbr", "body");
		parent("acronym", "body");
		parent("address", "body");
		parent("applet", "body");
		parent("area", "map");
		parent("b", "body");
		parent("base", "head");
		parent("basefont", "body");
		parent("bdo", "body");
		parent("big", "body");
		parent("blockquote", "body");
		parent("body", "html");
		parent("br", "body");
		parent("button", "form");
		parent("caption", "table");
		parent("center", "body");
		parent("cite", "body");
		parent("code", "body");
		parent("col", "table");
		parent("colgroup", "table");
		parent("dd", "dl");
		parent("del", "body");
		parent("dfn", "body");
		parent("dir", "body");
		parent("div", "body");
		parent("dl", "body");
		parent("dt", "dl");
		parent("em", "body");
		parent("fieldset", "form");
		parent("font", "body");
		parent("form", "body");
		parent("frame", "frameset");
		parent("frameset", "html");
		parent("h1", "body");
		parent("h2", "body");
		parent("h3", "body");
		parent("h4", "body");
		parent("h5", "body");
		parent("h6", "body");
		parent("head", "html");
		parent("hr", "body");
		parent("i", "body");
		parent("iframe", "body");
		parent("img", "body");
		parent("input", "form");
		parent("ins", "body");
		parent("isindex", "head");
		parent("kbd", "body");
		parent("label", "form");
		parent("legend", "fieldset");
		parent("li", "ul");
		parent("link", "head");
		parent("map", "body");
		parent("menu", "body");
		parent("meta", "head");
		parent("noframes", "html");
		parent("noscript", "body");
		parent("object", "body");
		parent("ol", "body");
		parent("optgroup", "select");
		parent("option", "select");
		parent("p", "body");
		parent("param", "object");
		parent("pre", "body");
		parent("q", "body");
		parent("s", "body");
		parent("samp", "body");
		parent("script", "html");
		parent("select", "form");
		parent("small", "body");
		parent("span", "body");
		parent("strike", "body");
		parent("strong", "body");
		parent("style", "head");
		parent("sub", "body");
		parent("sup", "body");
		parent("table", "body");
		parent("tbody", "table");
		parent("td", "tr");
		parent("textarea", "form");
		parent("tfoot", "table");
		parent("th", "tr");
		parent("thead", "table");
		parent("title", "head");
		parent("tr", "tbody");
		parent("tt", "body");
		parent("u", "body");
		parent("ul", "body");
		parent("var", "body");
		attribute("a", "hreflang", "NMTOKEN", null);
		attribute("a", "shape", "CDATA", "rect");
		attribute("a", "tabindex", "NMTOKEN", null);
		attribute("applet", "align", "NMTOKEN", null);
		attribute("area", "nohref", "BOOLEAN", null);
		attribute("area", "shape", "CDATA", "rect");
		attribute("area", "tabindex", "NMTOKEN", null);
		attribute("br", "clear", "CDATA", "none");
		attribute("button", "disabled", "BOOLEAN", null);
		attribute("button", "tabindex", "NMTOKEN", null);
		attribute("button", "type", "CDATA", "submit");
		attribute("caption", "align", "NMTOKEN", null);
		attribute("col", "align", "NMTOKEN", null);
		attribute("col", "span", "CDATA", "1");
		attribute("col", "valign", "NMTOKEN", null);
		attribute("colgroup", "align", "NMTOKEN", null);
		attribute("colgroup", "span", "CDATA", "1");
		attribute("colgroup", "valign", "NMTOKEN", null);
		attribute("dir", "compact", "BOOLEAN", null);
		attribute("div", "align", "NMTOKEN", null);
		attribute("dl", "compact", "BOOLEAN", null);
		attribute("form", "enctype", "CDATA", "application/x-www-form-urlencoded");
		attribute("form", "method", "CDATA", "get");
		attribute("frame", "frameborder", "CDATA", "1");
		attribute("frame", "noresize", "BOOLEAN", null);
		attribute("frame", "scrolling", "CDATA", "auto");
		attribute("h1", "align", "NMTOKEN", null);
		attribute("h2", "align", "NMTOKEN", null);
		attribute("h3", "align", "NMTOKEN", null);
		attribute("h4", "align", "NMTOKEN", null);
		attribute("h5", "align", "NMTOKEN", null);
		attribute("h6", "align", "NMTOKEN", null);
		attribute("hr", "align", "NMTOKEN", null);
		attribute("hr", "noshade", "BOOLEAN", null);
		attribute("iframe", "align", "NMTOKEN", null);
		attribute("iframe", "frameborder", "CDATA", "1");
		attribute("iframe", "scrolling", "CDATA", "auto");
		attribute("img", "align", "NMTOKEN", null);
		attribute("img", "ismap", "BOOLEAN", null);
		attribute("input", "align", "NMTOKEN", null);
		attribute("input", "checked", "BOOLEAN", null);
		attribute("input", "disabled", "BOOLEAN", null);
		attribute("input", "ismap", "BOOLEAN", null);
		attribute("input", "maxlength", "NMTOKEN", null);
		attribute("input", "readonly", "BOOLEAN", null);
		attribute("input", "tabindex", "NMTOKEN", null);
		attribute("input", "type", "CDATA", "text");
		attribute("label", "for", "IDREF", null);
		attribute("legend", "align", "NMTOKEN", null);
		attribute("li", "value", "NMTOKEN", null);
		attribute("link", "hreflang", "NMTOKEN", null);
		attribute("menu", "compact", "BOOLEAN", null);
		attribute("meta", "http-equiv", "NMTOKEN", null);
		attribute("meta", "name", "NMTOKEN", null);
		attribute("object", "align", "NMTOKEN", null);
		attribute("object", "declare", "BOOLEAN", null);
		attribute("object", "tabindex", "NMTOKEN", null);
		attribute("ol", "compact", "BOOLEAN", null);
		attribute("ol", "start", "NMTOKEN", null);
		attribute("optgroup", "disabled", "BOOLEAN", null);
		attribute("option", "disabled", "BOOLEAN", null);
		attribute("option", "selected", "BOOLEAN", null);
		attribute("p", "align", "NMTOKEN", null);
		attribute("param", "valuetype", "CDATA", "data");
		attribute("pre", "width", "NMTOKEN", null);
		attribute("script", "defer", "BOOLEAN", null);
		attribute("select", "disabled", "BOOLEAN", null);
		attribute("select", "multiple", "BOOLEAN", null);
		attribute("select", "size", "NMTOKEN", null);
		attribute("select", "tabindex", "NMTOKEN", null);
		attribute("table", "align", "NMTOKEN", null);
		attribute("table", "frame", "NMTOKEN", null);
		attribute("table", "rules", "NMTOKEN", null);
		attribute("tbody", "align", "NMTOKEN", null);
		attribute("tbody", "valign", "NMTOKEN", null);
		attribute("td", "align", "NMTOKEN", null);
		attribute("td", "colspan", "CDATA", "1");
		attribute("td", "headers", "IDREFS", null);
		attribute("td", "nowrap", "BOOLEAN", null);
		attribute("td", "rowspan", "CDATA", "1");
		attribute("td", "scope", "NMTOKEN", null);
		attribute("td", "valign", "NMTOKEN", null);
		attribute("textarea", "cols", "NMTOKEN", null);
		attribute("textarea", "disabled", "BOOLEAN", null);
		attribute("textarea", "readonly", "BOOLEAN", null);
		attribute("textarea", "rows", "NMTOKEN", null);
		attribute("textarea", "tabindex", "NMTOKEN", null);
		attribute("tfoot", "align", "NMTOKEN", null);
		attribute("tfoot", "valign", "NMTOKEN", null);
		attribute("th", "align", "NMTOKEN", null);
		attribute("th", "colspan", "CDATA", "1");
		attribute("th", "headers", "IDREFS", null);
		attribute("th", "nowrap", "BOOLEAN", null);
		attribute("th", "rowspan", "CDATA", "1");
		attribute("th", "scope", "NMTOKEN", null);
		attribute("th", "valign", "NMTOKEN", null);
		attribute("thead", "align", "NMTOKEN", null);
		attribute("thead", "valign", "NMTOKEN", null);
		attribute("tr", "align", "NMTOKEN", null);
		attribute("tr", "valign", "NMTOKEN", null);
		attribute("ul", "compact", "BOOLEAN", null);
		attribute("ul", "type", "NMTOKEN", null);
		attribute("a", "class", "NMTOKEN", null);
		attribute("abbr", "class", "NMTOKEN", null);
		attribute("acronym", "class", "NMTOKEN", null);
		attribute("address", "class", "NMTOKEN", null);
		attribute("applet", "class", "NMTOKEN", null);
		attribute("area", "class", "NMTOKEN", null);
		attribute("b", "class", "NMTOKEN", null);
		attribute("base", "class", "NMTOKEN", null);
		attribute("basefont", "class", "NMTOKEN", null);
		attribute("bdo", "class", "NMTOKEN", null);
		attribute("big", "class", "NMTOKEN", null);
		attribute("blockquote", "class", "NMTOKEN", null);
		attribute("body", "class", "NMTOKEN", null);
		attribute("br", "class", "NMTOKEN", null);
		attribute("button", "class", "NMTOKEN", null);
		attribute("caption", "class", "NMTOKEN", null);
		attribute("center", "class", "NMTOKEN", null);
		attribute("cite", "class", "NMTOKEN", null);
		attribute("code", "class", "NMTOKEN", null);
		attribute("col", "class", "NMTOKEN", null);
		attribute("colgroup", "class", "NMTOKEN", null);
		attribute("dd", "class", "NMTOKEN", null);
		attribute("del", "class", "NMTOKEN", null);
		attribute("dfn", "class", "NMTOKEN", null);
		attribute("dir", "class", "NMTOKEN", null);
		attribute("div", "class", "NMTOKEN", null);
		attribute("dl", "class", "NMTOKEN", null);
		attribute("dt", "class", "NMTOKEN", null);
		attribute("em", "class", "NMTOKEN", null);
		attribute("fieldset", "class", "NMTOKEN", null);
		attribute("font", "class", "NMTOKEN", null);
		attribute("form", "class", "NMTOKEN", null);
		attribute("frame", "class", "NMTOKEN", null);
		attribute("frameset", "class", "NMTOKEN", null);
		attribute("h1", "class", "NMTOKEN", null);
		attribute("h2", "class", "NMTOKEN", null);
		attribute("h3", "class", "NMTOKEN", null);
		attribute("h4", "class", "NMTOKEN", null);
		attribute("h5", "class", "NMTOKEN", null);
		attribute("h6", "class", "NMTOKEN", null);
		attribute("head", "class", "NMTOKEN", null);
		attribute("hr", "class", "NMTOKEN", null);
		attribute("html", "class", "NMTOKEN", null);
		attribute("i", "class", "NMTOKEN", null);
		attribute("iframe", "class", "NMTOKEN", null);
		attribute("img", "class", "NMTOKEN", null);
		attribute("input", "class", "NMTOKEN", null);
		attribute("ins", "class", "NMTOKEN", null);
		attribute("isindex", "class", "NMTOKEN", null);
		attribute("kbd", "class", "NMTOKEN", null);
		attribute("label", "class", "NMTOKEN", null);
		attribute("legend", "class", "NMTOKEN", null);
		attribute("li", "class", "NMTOKEN", null);
		attribute("link", "class", "NMTOKEN", null);
		attribute("map", "class", "NMTOKEN", null);
		attribute("menu", "class", "NMTOKEN", null);
		attribute("meta", "class", "NMTOKEN", null);
		attribute("noframes", "class", "NMTOKEN", null);
		attribute("noscript", "class", "NMTOKEN", null);
		attribute("object", "class", "NMTOKEN", null);
		attribute("ol", "class", "NMTOKEN", null);
		attribute("optgroup", "class", "NMTOKEN", null);
		attribute("option", "class", "NMTOKEN", null);
		attribute("p", "class", "NMTOKEN", null);
		attribute("param", "class", "NMTOKEN", null);
		attribute("pre", "class", "NMTOKEN", null);
		attribute("q", "class", "NMTOKEN", null);
		attribute("s", "class", "NMTOKEN", null);
		attribute("samp", "class", "NMTOKEN", null);
		attribute("script", "class", "NMTOKEN", null);
		attribute("select", "class", "NMTOKEN", null);
		attribute("small", "class", "NMTOKEN", null);
		attribute("span", "class", "NMTOKEN", null);
		attribute("strike", "class", "NMTOKEN", null);
		attribute("strong", "class", "NMTOKEN", null);
		attribute("style", "class", "NMTOKEN", null);
		attribute("sub", "class", "NMTOKEN", null);
		attribute("sup", "class", "NMTOKEN", null);
		attribute("table", "class", "NMTOKEN", null);
		attribute("tbody", "class", "NMTOKEN", null);
		attribute("td", "class", "NMTOKEN", null);
		attribute("textarea", "class", "NMTOKEN", null);
		attribute("tfoot", "class", "NMTOKEN", null);
		attribute("th", "class", "NMTOKEN", null);
		attribute("thead", "class", "NMTOKEN", null);
		attribute("title", "class", "NMTOKEN", null);
		attribute("tr", "class", "NMTOKEN", null);
		attribute("tt", "class", "NMTOKEN", null);
		attribute("u", "class", "NMTOKEN", null);
		attribute("ul", "class", "NMTOKEN", null);
		attribute("var", "class", "NMTOKEN", null);
		attribute("a", "dir", "NMTOKEN", null);
		attribute("abbr", "dir", "NMTOKEN", null);
		attribute("acronym", "dir", "NMTOKEN", null);
		attribute("address", "dir", "NMTOKEN", null);
		attribute("applet", "dir", "NMTOKEN", null);
		attribute("area", "dir", "NMTOKEN", null);
		attribute("b", "dir", "NMTOKEN", null);
		attribute("base", "dir", "NMTOKEN", null);
		attribute("basefont", "dir", "NMTOKEN", null);
		attribute("bdo", "dir", "NMTOKEN", null);
		attribute("big", "dir", "NMTOKEN", null);
		attribute("blockquote", "dir", "NMTOKEN", null);
		attribute("body", "dir", "NMTOKEN", null);
		attribute("br", "dir", "NMTOKEN", null);
		attribute("button", "dir", "NMTOKEN", null);
		attribute("caption", "dir", "NMTOKEN", null);
		attribute("center", "dir", "NMTOKEN", null);
		attribute("cite", "dir", "NMTOKEN", null);
		attribute("code", "dir", "NMTOKEN", null);
		attribute("col", "dir", "NMTOKEN", null);
		attribute("colgroup", "dir", "NMTOKEN", null);
		attribute("dd", "dir", "NMTOKEN", null);
		attribute("del", "dir", "NMTOKEN", null);
		attribute("dfn", "dir", "NMTOKEN", null);
		attribute("dir", "dir", "NMTOKEN", null);
		attribute("div", "dir", "NMTOKEN", null);
		attribute("dl", "dir", "NMTOKEN", null);
		attribute("dt", "dir", "NMTOKEN", null);
		attribute("em", "dir", "NMTOKEN", null);
		attribute("fieldset", "dir", "NMTOKEN", null);
		attribute("font", "dir", "NMTOKEN", null);
		attribute("form", "dir", "NMTOKEN", null);
		attribute("frame", "dir", "NMTOKEN", null);
		attribute("frameset", "dir", "NMTOKEN", null);
		attribute("h1", "dir", "NMTOKEN", null);
		attribute("h2", "dir", "NMTOKEN", null);
		attribute("h3", "dir", "NMTOKEN", null);
		attribute("h4", "dir", "NMTOKEN", null);
		attribute("h5", "dir", "NMTOKEN", null);
		attribute("h6", "dir", "NMTOKEN", null);
		attribute("head", "dir", "NMTOKEN", null);
		attribute("hr", "dir", "NMTOKEN", null);
		attribute("html", "dir", "NMTOKEN", null);
		attribute("i", "dir", "NMTOKEN", null);
		attribute("iframe", "dir", "NMTOKEN", null);
		attribute("img", "dir", "NMTOKEN", null);
		attribute("input", "dir", "NMTOKEN", null);
		attribute("ins", "dir", "NMTOKEN", null);
		attribute("isindex", "dir", "NMTOKEN", null);
		attribute("kbd", "dir", "NMTOKEN", null);
		attribute("label", "dir", "NMTOKEN", null);
		attribute("legend", "dir", "NMTOKEN", null);
		attribute("li", "dir", "NMTOKEN", null);
		attribute("link", "dir", "NMTOKEN", null);
		attribute("map", "dir", "NMTOKEN", null);
		attribute("menu", "dir", "NMTOKEN", null);
		attribute("meta", "dir", "NMTOKEN", null);
		attribute("noframes", "dir", "NMTOKEN", null);
		attribute("noscript", "dir", "NMTOKEN", null);
		attribute("object", "dir", "NMTOKEN", null);
		attribute("ol", "dir", "NMTOKEN", null);
		attribute("optgroup", "dir", "NMTOKEN", null);
		attribute("option", "dir", "NMTOKEN", null);
		attribute("p", "dir", "NMTOKEN", null);
		attribute("param", "dir", "NMTOKEN", null);
		attribute("pre", "dir", "NMTOKEN", null);
		attribute("q", "dir", "NMTOKEN", null);
		attribute("s", "dir", "NMTOKEN", null);
		attribute("samp", "dir", "NMTOKEN", null);
		attribute("script", "dir", "NMTOKEN", null);
		attribute("select", "dir", "NMTOKEN", null);
		attribute("small", "dir", "NMTOKEN", null);
		attribute("span", "dir", "NMTOKEN", null);
		attribute("strike", "dir", "NMTOKEN", null);
		attribute("strong", "dir", "NMTOKEN", null);
		attribute("style", "dir", "NMTOKEN", null);
		attribute("sub", "dir", "NMTOKEN", null);
		attribute("sup", "dir", "NMTOKEN", null);
		attribute("table", "dir", "NMTOKEN", null);
		attribute("tbody", "dir", "NMTOKEN", null);
		attribute("td", "dir", "NMTOKEN", null);
		attribute("textarea", "dir", "NMTOKEN", null);
		attribute("tfoot", "dir", "NMTOKEN", null);
		attribute("th", "dir", "NMTOKEN", null);
		attribute("thead", "dir", "NMTOKEN", null);
		attribute("title", "dir", "NMTOKEN", null);
		attribute("tr", "dir", "NMTOKEN", null);
		attribute("tt", "dir", "NMTOKEN", null);
		attribute("u", "dir", "NMTOKEN", null);
		attribute("ul", "dir", "NMTOKEN", null);
		attribute("var", "dir", "NMTOKEN", null);
		attribute("a", "id", "ID", null);
		attribute("abbr", "id", "ID", null);
		attribute("acronym", "id", "ID", null);
		attribute("address", "id", "ID", null);
		attribute("applet", "id", "ID", null);
		attribute("area", "id", "ID", null);
		attribute("b", "id", "ID", null);
		attribute("base", "id", "ID", null);
		attribute("basefont", "id", "ID", null);
		attribute("bdo", "id", "ID", null);
		attribute("big", "id", "ID", null);
		attribute("blockquote", "id", "ID", null);
		attribute("body", "id", "ID", null);
		attribute("br", "id", "ID", null);
		attribute("button", "id", "ID", null);
		attribute("caption", "id", "ID", null);
		attribute("center", "id", "ID", null);
		attribute("cite", "id", "ID", null);
		attribute("code", "id", "ID", null);
		attribute("col", "id", "ID", null);
		attribute("colgroup", "id", "ID", null);
		attribute("dd", "id", "ID", null);
		attribute("del", "id", "ID", null);
		attribute("dfn", "id", "ID", null);
		attribute("dir", "id", "ID", null);
		attribute("div", "id", "ID", null);
		attribute("dl", "id", "ID", null);
		attribute("dt", "id", "ID", null);
		attribute("em", "id", "ID", null);
		attribute("fieldset", "id", "ID", null);
		attribute("font", "id", "ID", null);
		attribute("form", "id", "ID", null);
		attribute("frame", "id", "ID", null);
		attribute("frameset", "id", "ID", null);
		attribute("h1", "id", "ID", null);
		attribute("h2", "id", "ID", null);
		attribute("h3", "id", "ID", null);
		attribute("h4", "id", "ID", null);
		attribute("h5", "id", "ID", null);
		attribute("h6", "id", "ID", null);
		attribute("head", "id", "ID", null);
		attribute("hr", "id", "ID", null);
		attribute("html", "id", "ID", null);
		attribute("i", "id", "ID", null);
		attribute("iframe", "id", "ID", null);
		attribute("img", "id", "ID", null);
		attribute("input", "id", "ID", null);
		attribute("ins", "id", "ID", null);
		attribute("isindex", "id", "ID", null);
		attribute("kbd", "id", "ID", null);
		attribute("label", "id", "ID", null);
		attribute("legend", "id", "ID", null);
		attribute("li", "id", "ID", null);
		attribute("link", "id", "ID", null);
		attribute("map", "id", "ID", null);
		attribute("menu", "id", "ID", null);
		attribute("meta", "id", "ID", null);
		attribute("noframes", "id", "ID", null);
		attribute("noscript", "id", "ID", null);
		attribute("object", "id", "ID", null);
		attribute("ol", "id", "ID", null);
		attribute("optgroup", "id", "ID", null);
		attribute("option", "id", "ID", null);
		attribute("p", "id", "ID", null);
		attribute("param", "id", "ID", null);
		attribute("pre", "id", "ID", null);
		attribute("q", "id", "ID", null);
		attribute("s", "id", "ID", null);
		attribute("samp", "id", "ID", null);
		attribute("script", "id", "ID", null);
		attribute("select", "id", "ID", null);
		attribute("small", "id", "ID", null);
		attribute("span", "id", "ID", null);
		attribute("strike", "id", "ID", null);
		attribute("strong", "id", "ID", null);
		attribute("style", "id", "ID", null);
		attribute("sub", "id", "ID", null);
		attribute("sup", "id", "ID", null);
		attribute("table", "id", "ID", null);
		attribute("tbody", "id", "ID", null);
		attribute("td", "id", "ID", null);
		attribute("textarea", "id", "ID", null);
		attribute("tfoot", "id", "ID", null);
		attribute("th", "id", "ID", null);
		attribute("thead", "id", "ID", null);
		attribute("title", "id", "ID", null);
		attribute("tr", "id", "ID", null);
		attribute("tt", "id", "ID", null);
		attribute("u", "id", "ID", null);
		attribute("ul", "id", "ID", null);
		attribute("var", "id", "ID", null);
		attribute("a", "lang", "NMTOKEN", null);
		attribute("abbr", "lang", "NMTOKEN", null);
		attribute("acronym", "lang", "NMTOKEN", null);
		attribute("address", "lang", "NMTOKEN", null);
		attribute("applet", "lang", "NMTOKEN", null);
		attribute("area", "lang", "NMTOKEN", null);
		attribute("b", "lang", "NMTOKEN", null);
		attribute("base", "lang", "NMTOKEN", null);
		attribute("basefont", "lang", "NMTOKEN", null);
		attribute("bdo", "lang", "NMTOKEN", null);
		attribute("big", "lang", "NMTOKEN", null);
		attribute("blockquote", "lang", "NMTOKEN", null);
		attribute("body", "lang", "NMTOKEN", null);
		attribute("br", "lang", "NMTOKEN", null);
		attribute("button", "lang", "NMTOKEN", null);
		attribute("caption", "lang", "NMTOKEN", null);
		attribute("center", "lang", "NMTOKEN", null);
		attribute("cite", "lang", "NMTOKEN", null);
		attribute("code", "lang", "NMTOKEN", null);
		attribute("col", "lang", "NMTOKEN", null);
		attribute("colgroup", "lang", "NMTOKEN", null);
		attribute("dd", "lang", "NMTOKEN", null);
		attribute("del", "lang", "NMTOKEN", null);
		attribute("dfn", "lang", "NMTOKEN", null);
		attribute("dir", "lang", "NMTOKEN", null);
		attribute("div", "lang", "NMTOKEN", null);
		attribute("dl", "lang", "NMTOKEN", null);
		attribute("dt", "lang", "NMTOKEN", null);
		attribute("em", "lang", "NMTOKEN", null);
		attribute("fieldset", "lang", "NMTOKEN", null);
		attribute("font", "lang", "NMTOKEN", null);
		attribute("form", "lang", "NMTOKEN", null);
		attribute("frame", "lang", "NMTOKEN", null);
		attribute("frameset", "lang", "NMTOKEN", null);
		attribute("h1", "lang", "NMTOKEN", null);
		attribute("h2", "lang", "NMTOKEN", null);
		attribute("h3", "lang", "NMTOKEN", null);
		attribute("h4", "lang", "NMTOKEN", null);
		attribute("h5", "lang", "NMTOKEN", null);
		attribute("h6", "lang", "NMTOKEN", null);
		attribute("head", "lang", "NMTOKEN", null);
		attribute("hr", "lang", "NMTOKEN", null);
		attribute("html", "lang", "NMTOKEN", null);
		attribute("i", "lang", "NMTOKEN", null);
		attribute("iframe", "lang", "NMTOKEN", null);
		attribute("img", "lang", "NMTOKEN", null);
		attribute("input", "lang", "NMTOKEN", null);
		attribute("ins", "lang", "NMTOKEN", null);
		attribute("isindex", "lang", "NMTOKEN", null);
		attribute("kbd", "lang", "NMTOKEN", null);
		attribute("label", "lang", "NMTOKEN", null);
		attribute("legend", "lang", "NMTOKEN", null);
		attribute("li", "lang", "NMTOKEN", null);
		attribute("link", "lang", "NMTOKEN", null);
		attribute("map", "lang", "NMTOKEN", null);
		attribute("menu", "lang", "NMTOKEN", null);
		attribute("meta", "lang", "NMTOKEN", null);
		attribute("noframes", "lang", "NMTOKEN", null);
		attribute("noscript", "lang", "NMTOKEN", null);
		attribute("object", "lang", "NMTOKEN", null);
		attribute("ol", "lang", "NMTOKEN", null);
		attribute("optgroup", "lang", "NMTOKEN", null);
		attribute("option", "lang", "NMTOKEN", null);
		attribute("p", "lang", "NMTOKEN", null);
		attribute("param", "lang", "NMTOKEN", null);
		attribute("pre", "lang", "NMTOKEN", null);
		attribute("q", "lang", "NMTOKEN", null);
		attribute("s", "lang", "NMTOKEN", null);
		attribute("samp", "lang", "NMTOKEN", null);
		attribute("script", "lang", "NMTOKEN", null);
		attribute("select", "lang", "NMTOKEN", null);
		attribute("small", "lang", "NMTOKEN", null);
		attribute("span", "lang", "NMTOKEN", null);
		attribute("strike", "lang", "NMTOKEN", null);
		attribute("strong", "lang", "NMTOKEN", null);
		attribute("style", "lang", "NMTOKEN", null);
		attribute("sub", "lang", "NMTOKEN", null);
		attribute("sup", "lang", "NMTOKEN", null);
		attribute("table", "lang", "NMTOKEN", null);
		attribute("tbody", "lang", "NMTOKEN", null);
		attribute("td", "lang", "NMTOKEN", null);
		attribute("textarea", "lang", "NMTOKEN", null);
		attribute("tfoot", "lang", "NMTOKEN", null);
		attribute("th", "lang", "NMTOKEN", null);
		attribute("thead", "lang", "NMTOKEN", null);
		attribute("title", "lang", "NMTOKEN", null);
		attribute("tr", "lang", "NMTOKEN", null);
		attribute("tt", "lang", "NMTOKEN", null);
		attribute("u", "lang", "NMTOKEN", null);
		attribute("ul", "lang", "NMTOKEN", null);
		attribute("var", "lang", "NMTOKEN", null);
		entity("aacute", '\u00E1');
		entity("Aacute", '\u00C1');
		entity("acirc", '\u00E2');
		entity("Acirc", '\u00C2');
		entity("acute", '\u00B4');
		entity("aelig", '\u00E6');
		entity("AElig", '\u00C6');
		entity("agrave", '\u00E0');
		entity("Agrave", '\u00C0');
		entity("alefsym", '\u2135');
		entity("alpha", '\u03B1');
		entity("Alpha", '\u0391');
		entity("amp", '\u0026');
		entity("and", '\u2227');
		entity("ang", '\u2220');
		entity("apos", '\'');
		entity("aring", '\u00E5');
		entity("Aring", '\u00C5');
		entity("asymp", '\u2248');
		entity("atilde", '\u00E3');
		entity("Atilde", '\u00C3');
		entity("auml", '\u00E4');
		entity("Auml", '\u00C4');
		entity("bdquo", '\u201E');
		entity("beta", '\u03B2');
		entity("Beta", '\u0392');
		entity("brvbar", '\u00A6');
		entity("bull", '\u2022');
		entity("cap", '\u2229');
		entity("ccedil", '\u00E7');
		entity("Ccedil", '\u00C7');
		entity("cedil", '\u00B8');
		entity("cent", '\u00A2');
		entity("chi", '\u03C7');
		entity("Chi", '\u03A7');
		entity("circ", '\u02C6');
		entity("clubs", '\u2663');
		entity("cong", '\u2245');
		entity("copy", '\u00A9');
		entity("crarr", '\u21B5');
		entity("cup", '\u222A');
		entity("curren", '\u00A4');
		entity("dagger", '\u2020');
		entity("Dagger", '\u2021');
		entity("darr", '\u2193');
		entity("dArr", '\u21D3');
		entity("deg", '\u00B0');
		entity("delta", '\u03B4');
		entity("Delta", '\u0394');
		entity("diams", '\u2666');
		entity("divide", '\u00F7');
		entity("eacute", '\u00E9');
		entity("Eacute", '\u00C9');
		entity("ecirc", '\u00EA');
		entity("Ecirc", '\u00CA');
		entity("egrave", '\u00E8');
		entity("Egrave", '\u00C8');
		entity("empty", '\u2205');
		entity("emsp", '\u2003');
		entity("ensp", '\u2002');
		entity("epsilon", '\u03B5');
		entity("Epsilon", '\u0395');
		entity("equiv", '\u2261');
		entity("eta", '\u03B7');
		entity("Eta", '\u0397');
		entity("eth", '\u00F0');
		entity("ETH", '\u00D0');
		entity("euml", '\u00EB');
		entity("Euml", '\u00CB');
		entity("euro", '\u20AC');
		entity("exist", '\u2203');
		entity("fnof", '\u0192');
		entity("forall", '\u2200');
		entity("frac12", '\u00BD');
		entity("frac14", '\u00BC');
		entity("frac34", '\u00BE');
		entity("frasl", '\u2044');
		entity("gamma", '\u03B3');
		entity("Gamma", '\u0393');
		entity("ge", '\u2265');
		entity("gt", '\u003E');
		entity("harr", '\u2194');
		entity("hArr", '\u21D4');
		entity("hearts", '\u2665');
		entity("hellip", '\u2026');
		entity("iacute", '\u00ED');
		entity("Iacute", '\u00CD');
		entity("icirc", '\u00EE');
		entity("Icirc", '\u00CE');
		entity("iexcl", '\u00A1');
		entity("igrave", '\u00EC');
		entity("Igrave", '\u00CC');
		entity("image", '\u2111');
		entity("infin", '\u221E');
		entity("int", '\u222B');
		entity("iota", '\u03B9');
		entity("Iota", '\u0399');
		entity("iquest", '\u00BF');
		entity("isin", '\u2208');
		entity("iuml", '\u00EF');
		entity("Iuml", '\u00CF');
		entity("kappa", '\u03BA');
		entity("Kappa", '\u039A');
		entity("lambda", '\u03BB');
		entity("Lambda", '\u039B');
		entity("lang", '\u2329');
		entity("laquo", '\u00AB');
		entity("larr", '\u2190');
		entity("lArr", '\u21D0');
		entity("lceil", '\u2308');
		entity("ldquo", '\u201C');
		entity("le", '\u2264');
		entity("lfloor", '\u230A');
		entity("lowast", '\u2217');
		entity("loz", '\u25CA');
		entity("lrm", '\u200E');
		entity("lsaquo", '\u2039');
		entity("lsquo", '\u2018');
		entity("lt", '\u003C');
		entity("macr", '\u00AF');
		entity("mdash", '\u2014');
		entity("micro", '\u00B5');
		entity("middot", '\u00B7');
		entity("minus", '\u2212');
		entity("mu", '\u03BC');
		entity("Mu", '\u039C');
		entity("nabla", '\u2207');
		entity("nbsp", '\u00A0');
		entity("ndash", '\u2013');
		entity("ne", '\u2260');
		entity("ni", '\u220B');
		entity("not", '\u00AC');
		entity("notin", '\u2209');
		entity("nsub", '\u2284');
		entity("ntilde", '\u00F1');
		entity("Ntilde", '\u00D1');
		entity("nu", '\u03BD');
		entity("Nu", '\u039D');
		entity("oacute", '\u00F3');
		entity("Oacute", '\u00D3');
		entity("ocirc", '\u00F4');
		entity("Ocirc", '\u00D4');
		entity("oelig", '\u0153');
		entity("OElig", '\u0152');
		entity("ograve", '\u00F2');
		entity("Ograve", '\u00D2');
		entity("oline", '\u203E');
		entity("omega", '\u03C9');
		entity("Omega", '\u03A9');
		entity("omicron", '\u03BF');
		entity("Omicron", '\u039F');
		entity("oplus", '\u2295');
		entity("or", '\u2228');
		entity("ordf", '\u00AA');
		entity("ordm", '\u00BA');
		entity("oslash", '\u00F8');
		entity("Oslash", '\u00D8');
		entity("otilde", '\u00F5');
		entity("Otilde", '\u00D5');
		entity("otimes", '\u2297');
		entity("ouml", '\u00F6');
		entity("Ouml", '\u00D6');
		entity("para", '\u00B6');
		entity("part", '\u2202');
		entity("permil", '\u2030');
		entity("perp", '\u22A5');
		entity("phi", '\u03C6');
		entity("Phi", '\u03A6');
		entity("pi", '\u03C0');
		entity("Pi", '\u03A0');
		entity("piv", '\u03D6');
		entity("plusmn", '\u00B1');
		entity("pound", '\u00A3');
		entity("prime", '\u2032');
		entity("Prime", '\u2033');
		entity("prod", '\u220F');
		entity("prop", '\u221D');
		entity("psi", '\u03C8');
		entity("Psi", '\u03A8');
		entity("quot", '\u0022');
		entity("radic", '\u221A');
		entity("rang", '\u232A');
		entity("raquo", '\u00BB');
		entity("rarr", '\u2192');
		entity("rArr", '\u21D2');
		entity("rceil", '\u2309');
		entity("rdquo", '\u201D');
		entity("real", '\u211C');
		entity("reg", '\u00AE');
		entity("rfloor", '\u230B');
		entity("rho", '\u03C1');
		entity("Rho", '\u03A1');
		entity("rlm", '\u200F');
		entity("rsaquo", '\u203A');
		entity("rsquo", '\u2019');
		entity("sbquo", '\u201A');
		entity("scaron", '\u0161');
		entity("Scaron", '\u0160');
		entity("sdot", '\u22C5');
		entity("sect", '\u00A7');
		entity("shy", '\u00AD');
		entity("sigma", '\u03C3');
		entity("Sigma", '\u03A3');
		entity("sigmaf", '\u03C2');
		entity("sim", '\u223C');
		entity("spades", '\u2660');
		entity("sub", '\u2282');
		entity("sube", '\u2286');
		entity("sum", '\u2211');
		entity("sup", '\u2283');
		entity("sup1", '\u00B9');
		entity("sup2", '\u00B2');
		entity("sup3", '\u00B3');
		entity("supe", '\u2287');
		entity("szlig", '\u00DF');
		entity("tau", '\u03C4');
		entity("Tau", '\u03A4');
		entity("there4", '\u2234');
		entity("theta", '\u03B8');
		entity("Theta", '\u0398');
		entity("thetasym", '\u03D1');
		entity("thinsp", '\u2009');
		entity("thorn", '\u00FE');
		entity("THORN", '\u00DE');
		entity("tilde", '\u02DC');
		entity("times", '\u00D7');
		entity("trade", '\u2122');
		entity("uacute", '\u00FA');
		entity("Uacute", '\u00DA');
		entity("uarr", '\u2191');
		entity("uArr", '\u21D1');
		entity("ucirc", '\u00FB');
		entity("Ucirc", '\u00DB');
		entity("ugrave", '\u00F9');
		entity("Ugrave", '\u00D9');
		entity("uml", '\u00A8');
		entity("upsih", '\u03D2');
		entity("upsilon", '\u03C5');
		entity("Upsilon", '\u03A5');
		entity("uuml", '\u00FC');
		entity("Uuml", '\u00DC');
		entity("weierp", '\u2118');
		entity("xi", '\u03BE');
		entity("Xi", '\u039E');
		entity("yacute", '\u00FD');
		entity("Yacute", '\u00DD');
		entity("yen", '\u00A5');
		entity("yuml", '\u00FF');
		entity("Yuml", '\u0178');
		entity("zeta", '\u03B6');
		entity("Zeta", '\u0396');
		entity("zwj", '\u200D');
		entity("zwnj", '\u200C');

		// End of Schema calls
		}


	}
