<h1>Testing</h1>

When running a test make sure to add the following CLI argument
<br/>
<b>-ea --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED</b>
<br/>
This solves a permission error caused by javafx.

Make sure to also include the ENVIRONMENT env variable which specifies the 

