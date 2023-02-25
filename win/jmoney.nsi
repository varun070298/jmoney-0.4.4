;NSIS
;JMoney Installer Script

!define MUI_PRODUCT "JMoney"
!define MUI_VERSION "0.4.4"

!include "MUI.nsh"

;--------------------------------
;Configuration

  ;General
  OutFile "..\deploy\jmoney-${MUI_VERSION}-setup.exe"

  ;Folder selection page
  InstallDir "$PROGRAMFILES\${MUI_PRODUCT}"
  
  ;Remember install folder
  InstallDirRegKey HKCU "Software\${MUI_PRODUCT}" ""
  
  ;$9 is being used to store the Start Menu Folder.
  ;Do not use this variable in your script (or Push/Pop it)!

  ;To change this variable, use MUI_STARTMENUPAGE_VARIABLE.
  ;Have a look at the Readme for info about other options (default folder,
  ;registry).

  ;Remember the Start Menu Folder
  !define MUI_STARTMENUPAGE_REGISTRY_ROOT "HKCU" 
  !define MUI_STARTMENUPAGE_REGISTRY_KEY "Software\${MUI_PRODUCT}" 
  !define MUI_STARTMENUPAGE_REGISTRY_VALUENAME "Start Menu Folder"

  !define TEMP $R0
  
;--------------------------------
;Modern UI Configuration

  !define MUI_LICENSEPAGE
  !define MUI_COMPONENTSPAGE
  !define MUI_DIRECTORYPAGE
  !define MUI_STARTMENUPAGE
  
  !define MUI_ABORTWARNING
  
  !define MUI_UNINSTALLER
  !define MUI_UNCONFIRMPAGE
  
;--------------------------------
;Languages
 
  !insertmacro MUI_LANGUAGE "English"
  
;--------------------------------
;Language Strings

  ;Description
  LangString DESC_SecCore ${LANG_ENGLISH} "The core files required to run JMoney."

;--------------------------------
;Data
  
  LicenseData "..\LICENSE.txt"

;--------------------------------
;Installer Sections

Section "JMoney" SecCore
  SectionIn RO
  SetOverwrite on

  SetOutPath $INSTDIR\lib
  File ..\lib\*.jar

  SetOutPath "$INSTDIR"
  File "..\jmoney.exe"
  File "..\*.txt"
    
  ;Store install folder
  WriteRegStr HKLM "Software\${MUI_PRODUCT}" "Install_Dir" $INSTDIR

  ; Write the uninstall keys for Windows
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${MUI_PRODUCT}" "DisplayName" "JMoney"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${MUI_PRODUCT}" "UninstallString" '"$INSTDIR\uninstall.exe"'
  
  !insertmacro MUI_STARTMENU_WRITE_BEGIN
    
  ;Create shortcuts
  CreateDirectory "$SMPROGRAMS\${MUI_STARTMENUPAGE_VARIABLE}"
  CreateShortCut "$SMPROGRAMS\${MUI_STARTMENUPAGE_VARIABLE}\JMoney.lnk" "$INSTDIR\jmoney.exe"
  CreateShortCut "$SMPROGRAMS\${MUI_STARTMENUPAGE_VARIABLE}\Uninstall.lnk" "$INSTDIR\uninstall.exe"
    
  !insertmacro MUI_STARTMENU_WRITE_END
  
  ;Create uninstaller
  WriteUninstaller "$INSTDIR\uninstall.exe"

SectionEnd

;Display the Finish header
;Insert this macro after the sections if you are not using a finish page
!insertmacro MUI_SECTIONS_FINISHHEADER

;--------------------------------
;Descriptions

!insertmacro MUI_FUNCTIONS_DESCRIPTION_BEGIN
  !insertmacro MUI_DESCRIPTION_TEXT ${SecCore} $(DESC_SecCore)
!insertmacro MUI_FUNCTIONS_DESCRIPTION_END
 
;--------------------------------
;Uninstaller Section

Section "Uninstall"

  Delete "$INSTDIR\jmoney.exe"
  Delete "$INSTDIR\*.txt"
  Delete "$INSTDIR\uninstall.exe"
  RMDir /r $INSTDIR\lib
  
  ;Remove shortcut
  ReadRegStr ${TEMP} "${MUI_STARTMENUPAGE_REGISTRY_ROOT}" "${MUI_STARTMENUPAGE_REGISTRY_KEY}" "${MUI_STARTMENUPAGE_REGISTRY_VALUENAME}"
  StrCmp ${TEMP} "" noshortcuts
    Delete "$SMPROGRAMS\${TEMP}\JMoney.lnk"
    Delete "$SMPROGRAMS\${TEMP}\Uninstall.lnk"
    RMDir "$SMPROGRAMS\${TEMP}" ;Only if empty, so it won't delete other shortcuts

  noshortcuts:

  RMDir "$INSTDIR"

  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${MUI_PRODUCT}"
  DeleteRegKey HKLM "Software\${MUI_PRODUCT}"

  ;Display the Finish header
  !insertmacro MUI_UNFINISHHEADER

SectionEnd