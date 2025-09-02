Specification Heading
=====================
Created by alper on 2.09.2025

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.
     
## Create task with status
* Create task that
  |title           |description       |status|
  |----------------|------------------|------|
  |grocery shopping|milk, tea, coffee |TODO  |

* Response status code should be "201"
* Response type should be "TaskResponse"
* Task "title" should be "grocery shopping"
* Task "description" should be "milk, tea, coffee"
* Task "status" should be "TODO"


## Create Task without status response should be TODO
* Create task that
  |title           |description           |
  |----------------|----------------------|
  |homeworks       |math, science, history|

* Response status code should be "201"
* Response type should be "TaskResponse"
* Task "title" should be "homeworks"
* Task "description" should be "math, science, history"
* Task "status" should be "TODO"


## Update task with incorret argument
* Create task that
  |title     |description|status|
  |----------|-----------|------|
  |do        |John, Alice|TODO  |

* Response status code should be "400"
