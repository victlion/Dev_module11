<!DOCTYPE html>
<html>
<head>
    <title>Servlet</title>
</head>
<body>
<div style="text-align: center;">
    <h2>Start page Servlet</h2>
    <p>Test button.</p>
    <br>
    <br>
    <input type="button" onclick="location.href='/Dev_module11/time';" value="/TIME"/>
    <br>
    <br>
    <input type="button" onclick="location.href='/Dev_module11/time?timezone=UTC%2B2';" value="/UTC+2"/>
    <br>
    <br>
    <input type="button" onclick="location.href='/Dev_module11/time?timezone=UTC%2B20';" value="/UTC+20(code 400)"/>
</div>
</body>
</html>