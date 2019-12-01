/system script add name=identity source=":local ip X.X.X.X;\r\
    \n\r\
    \n:local interfaces [/interface find];\r\
    \n:foreach interface in=\$interfaces do={\r\
    \n\t:local interfaceName [/interface get \$interface name];\r\
    \n\t:do {\r\
    \n\t\t:local interfaceIP [/ip address get [find interface=\$interfaceName] address ];\r\
    \n\t\t:if (\$ip in \$interfaceIP) do={\r\
    \n\t\t\t:local interfaceType [/interface get \$interface type];\r\
    \n\t\t\t:if ([/find \"pppoe-out\" \$interfaceType] = 0) do={\r\
    \n\t\t\t\t:local pppoeUser [/interface pppoe-client get \$interfaceName user];\r\
    \n\t\t\t\t/system identity set name=\$pppoeUser;\r\
    \n\t\t\t};\r\
    \n\t\t};\r\
    \n\t} on-error={};\r\
    \n};\r\
	\n/file remove identity.rsc\r\
    \n/system script remove identity"