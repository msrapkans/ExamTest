


Add remote directlry and Push
git remote add demo https://github.com/msrapkans/demo.git
$ git remote -v
$ git push -u demo main (renamed Master to Main then push)

Check status , add , log , rename etc.etc.
$ git status
$ git add .
$ git commit -m " successful postman request for user creation"
$ git log
$ git reflog
$ git branch -m master main (rename Master Branch)
$ git log --oneline --graph --decorate --all
$ git log -h
$ git log --oneline
$ git config --global user.email "msransari2@gmail.com"
$ git config --global user.name "M Sajid Raza"



To Clone from specific commit
Create new directory

mkdir ExamTest
cd ExamTest
ls -lrt
git init
git remote add demo https://github.com/msrapkans/demo.git
$ git fetch demo 7b24ab433a2eee777c149a69d3a7f3ce77c6cdb7 (this is specific hash)
$ git checkout FETCH_HEAD



Test Data

{
"email": "john.doe@example.com",
"name": "John Doe",
"password": "gfgfdg",
"image": "https://example.com",
"enable": true,
"provider": "LOCAL",
"roles":  []
}