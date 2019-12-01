/system script add name=disableMacServer\
 source="/tool mac-server set allowed-interface-list=none\r\
    \n/tool mac-server mac-winbox set allowed-interface-list=none\r\
    \n/tool mac-server ping set enabled=no\r\
    \n/file remove disableMacServer.rsc\r\
    \n/system script remove disableMacServer"