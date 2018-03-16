#Git 
---
- Track remote branch: **git branch --track branch-name origin/branch-name**
- Add all files to a commit except a single file: **git add -u; git reset -- excludeFilePath**
- Ignore modified but not commit files in git: **git update-index --assume-unchanged filePath**

#PSCP Usage
---
- Copy a single file from local to remote: **pscp c:/file.txt username@ip:/home/file.txt**
- Copy directory from local to remote: **pscp -r c:/app/ username@ip:/home/app**
- Copy directory from remote to local: **pscp -r username@ip:/home/app/\* c:\app**

#List installed modules with Nginx
---
- nginx -V 2>&1 | tr -- - '\n' | grep module

# Find out version of running tomcat
---
- java -cp catalina.jar org.apache.catalina.util.ServerInfo





