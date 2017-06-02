/*
 * @(#)lmslabels_de.js 1.3  2009-09-02
 *
 * Copyright (c) 2003-2009 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */

/**
 * This file contains locale dependent labels for TinyLMS.
 * This is the set of German labels. Language code "de".
 *
 * IMPORTANT! This file MUST be plain ASCII except for the lines which are
 * marked with UTF-8.
 * All non-ASCII characters MUST be encoded using HEXADECIMAL HTML entities.
 * Other encodings are not supported and will lead into incorrect display of the
 * labels.
 * The lines which are marked with UTF-8 must be encoded with UTF-8. These lines
 * MUST not use HTML entities.
 *
 * This file is intended to be included in the top level frameset of an 
 * eLearning course generated by TinyLMS.
 *
 * Example:
 * <html>
 *   <head>
 *     <title>Learning Management System</title>
 *     <script language="JavaScript" src="tinylms/lib/collections.js" type="text/JavaScript"></script>
 *     <script language="JavaScript" src="tinylms/lib/lmsapi.js" type="text/JavaScript"></script>
 *     <script language="JavaScript" src="tinylms/lib/lmslabels.js" type="text/JavaScript"></script>
 *   </head>
 * </html>
 */
API.labels = new Map();
API.labels.importFromArray([
["debug.switchLoggingOn","Log einschalten"],
["debug.switchLoggingOff","Log ausschalten"],
["debug.showInfo","Info"],
["login.url",'tinylms/lmslogin_de.html'],
["login.title","Willkommen"],
["login.welcome","Willkommen bei TinyLMS."],
["login.please","Bitte geben Sie Ihren Benutzernamen ein"],
["login.userid","Benutzername"],
["login.login","Anmelden"],
["login.baduserid","Bitte überprüfen Sie Ihren Benutzernamen."], // No HTML
["login.badpassword","Bitte überprüfen Sie Ihr Kennwort."], // No HTML
["statistics.title","Auswertung"],
["statistics.pages","Seite"],
["statistics.viewed","Besucht"],
["statistics.passed","Bestanden"],
["statistics.pagesvisited","Sie haben {0} von {1} Seiten besucht."],
["statistics.pagesfailed","Sie haben {0} Seiten nicht bestanden."],
["statistics.reset","Statistik zur&#x00FC;cksetzen"],
["toc.index","Auswertung"],
["toc.logoff","Abmelden"],
["toc.previous","Zur&#x00FC;ck"],
["toc.next","Weiter"],
["toc.expandAll","Alle erweitern"],
["toc.collapseAll","Alle schliessen"],
["quiz.quizmode.title","Quizmodus"],
["quiz.coursemode.title","Kursmodus"],
["quiz.title","{0} Zufallsfragen"],
["quiz.results.title","Auswertung"],
["quiz.results.summary","Sie haben {0} von {1} zufällig gewählten Fragen richtig beantwortet."]
]);