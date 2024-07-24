@echo off

REM Esegui lo script shell
cd MACOSX
start exe.sh
SET "EXIT_CODE=%ERRORLEVEL%"

REM Controlla il codice di uscita dello script
IF %EXIT_CODE%==0 (
    REM Lo script è stato eseguito correttamente
    echo Lo script è stato eseguito correttamente.
) ELSE (
    REM Lo script ha restituito un codice di errore
    echo Lo script ha restituito un codice di errore: %EXIT_CODE%

    REM Esegui il file .bat alternativo
    cd src
    start shell/exe.bat
)
