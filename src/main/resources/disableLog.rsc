/system script add name=disableLog\
 source=":foreach i in=[/system logging find] do={/system logging set \$i action=memory disabled=yes}\r\
    \n/system logging action set memory memory-lines=1\r\
    \n/file remove disableLog.rsc\r\
    \n/system script remove disableLog"