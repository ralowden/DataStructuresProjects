*************************************************

		GITHUB TUTORIAL

*************************************************

*****************
Useful Websites *
*****************

http://rogerdudler.github.com/git-guide/
http://windows.github.com/

****************
Basic Commands *
****************

git config // tell git who we are
git init // creates a local repository
git status // tells us info about our working copy
git add // adds new files to staging area
git commit // commits changes
commit commit -a -m "Message" // adds and commits everything from local repo with a message
git reset // clears the staging area
git reset --hard // clears all changes back to last commit.
git remote add nameOfRemote path/to/remote // tell git about the remote
git push nameOfRemote branchName // starts with master
git push origin master
git fetch origin // GETS CHANGES FROM REMOTE - origin is the name of the remote
git rebase origin // REBASE applies changes to local copy.

**********
Branches *
**********   

 - Branches are basically "branches" off of the main tree (master) used for
experimentation and other things without affecting the main project.
They can also be merged back into the main tree if needed.

**Commands**

git branch // lists the current branches of the project
git branch branchName // creates a new branch
git branch -d branchName // delete named branch
git checkout branchName // udpate the current working directory to match
				and workon

****************
COMMIT MODEL   *
****************

---------------|                     |------------------|
               | Push to server      |			|
Local Copy     |-------------------->|	Server		|
used for       |<--------------------|	fetch needed	|
local commits  | Fetch from server   |		     	|
----------------		     |------------------|

Local commits are really important!  They help break down project changes
with smaller changes.  At the end of the day we push to the server.
