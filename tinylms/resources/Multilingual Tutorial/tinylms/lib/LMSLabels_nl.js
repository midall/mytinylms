/*
 * @(#)lmslabels_nl.js 1.3  2009-09-02
 *
 * Copyright (c) 2003-2009 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 *
 * Dutch translation by Jean Pierre Vandecandelaere - 2006-06-12
 */

/**
 * This file contains locale dependent labels for TinyLMS.
 * This is the set of Dutch labels. Language code "nl".
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
 *     <script language="JavaScript" src="tinylms/lib/lmslabels.js" type="text/JavaScript"></script>
 *     <script language="JavaScript" src="tinylms/lib/lmslabels_nl.js" type="text/JavaScript"></script>
 *   </head>
 * </html>
 */
API.labels = new Map();
API.labels.importFromArray([
["debug.switchLoggingOn","Schakel Logging in"],
["debug.switchLoggingOff","Schakel Logging uit"],
["debug.showInfo","Toon Info"],
["login.url",'tinylms/lmslogin_nl.html'],
["login.title","Welkom"],
["login.welcome","Welkom bij TinyLMS."],
["login.please","Vul uw gebruikersnaam in a.u.b."],
["login.userid","Gebruikersnaam:"],
["login.login","Log in"],
["login.baduserid","Verif\ufffdeer uw gebruikers ID a.u.b."], // UTF-8
["login.badpassword","Verif\ufffdeer uw paswoord a.u.b."], // UTF-8
["statistics.title","Statistieken"],
["statistics.pages","Pagina"],
["statistics.viewed","Bekeken"],
["statistics.passed","Geslaagd"],
["statistics.pagesvisited","U heeft {0} van {1} paginas bezocht."],
["statistics.pagesfailed","U heeft {0} oefeningen fout."],
["statistics.reset","Reset"],
["toc.logoff","Log uit"],
["toc.index","Index"],
["toc.previous","Vorige"],
["toc.next","Volgende"],
["toc.expandAll","Alles uitklappen"],
["toc.collapseAll","Alles samenklappen"],
["quiz.title","{0} Random Questions"],
["quiz.quizmode.title","Quiz Mode"],
["quiz.coursemode.title","Course Mode"],
["quiz.results.title","Results"],
["quiz.results.summary","You have passed {0} out of {1} randomly chosen questions."]
]);