<h1>BS2202 Byron Lloyd - Wakeman</h1>

<h2>Introduction</h2>

This is the repo for the summative assessment for module BS2202

<h2>Tech Stack</h2>

Java and that

<h2>Installation</h2>

Once the repo has been cloned or forked, mvn clean install will need to be ran to install all the required dependencies. <br/>
If the javafx imports are giving the error 'not resolved' make sure to reload the project by right clicking the pom.xml file, navigaing to maven and clicking 'Reload Project'.
<br/>
<br/>
![image](https://github.com/byronlloydwakeman/SummativeUI/assets/72659565/2a0d6d53-da9b-4c99-9546-57c3295fc4da)



<h2>Testing</h2>

When running a test make sure to add the following CLI argument
<br/>
<b>-ea --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED</b>
<br/>
This solves a permission error caused by javafx.

Make sure to also include the ENVIRONMENT env variable which specifies the given environment the code will be ran in. The available options are 'test' and 'production'

