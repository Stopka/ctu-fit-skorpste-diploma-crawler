setlocal enabledelayedexpansion
set params=
:Loop
SHIFT
IF "%0"=="" GOTO Continue
  SET param=%0
  IF NOT %param:~0,2%==-D GOTO NotStart
  SET params=!params! !param!
  SET num=0
  GOTO Loop
  :NotStart
  IF %num%==0 GOTO Eql 
  SET params=!params!,!param!
  GOTO Loop
  :Eql
  SET params=!params!=!param!
  SET num=1
  GOTO Loop
:Continue
java %params% -jar crawler.jar