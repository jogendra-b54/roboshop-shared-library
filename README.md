# roboshop-shared-library


This repository contains all the common patterns of the CI and CD pipelines of the RoboSHop e-commerce project and it helps in avoiding the repetivite code and make us close to DRY approach.


```
As Pipeline is adopted for more and more projects in an organization, common patterns are likely to emerge. Oftentimes it is useful to share parts of Pipelines between various projects to reduce redundancies and keep code "DRY" [1].

Pipeline has support for creating "Shared Libraries" which can be defined in external source control repositories and loaded into existing Pipelines.

```


### Directory structure
```
(root)
+- src                     # Groovy source files
|   +- org
|       +- foo
|           +- Bar.groovy  # for org.foo.Bar class
+- vars
|   +- foo.groovy          # for global 'foo' variable
|   +- foo.txt             # help for 'foo' variable
+- resources               # resource files (external libraries only)
|   +- org
|       +- foo
|           +- bar.json    # static helper data for org.foo.Bar

```
# What srs has ?
    The src directory should look like standard Java source directory structure. This directory is added to the classpath when executing Pipelines.

# What vars has ?
    The vars directory hosts script files that are exposed as a variable in Pipelines. The name of the file is the name of the variable in the Pipeline. So if you had a file called vars/log.groovy with a function like def info(message)…​ in it, you can access this function like log.info "hello world" in the Pipeline. You can put as many functions as you like inside this file. Read on below for more examples and options.

Reference:  'jchttps://www.jenkins.io/doc/book/pipeline/shared-libraries/'