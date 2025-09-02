Specification Heading
=====================
Created by alper on 2.09.2025

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.

## Update task with correct argument
* Update task that
  |id|title     |description|status|
  |--|----------|-----------|------|
  |1 |make calls|John, Alice|DONE  |

* Response status code should be "202"
* Response type should be "TaskResponse"
* Task "id" should be "1"
* Task "title" should be "make calls"
* Task "description" should be "John, Alice"
* Task "status" should be "DONE"


## Update task with incorret argument
* Update task that
  |id|title     |description|status|
  |--|----------|-----------|------|
  |1 |make calls|do         |TODO  |

* Response status code should be "400"
