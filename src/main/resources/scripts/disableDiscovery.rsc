/system script add name=disableDiscovery\
 source="/ip neighbor discovery-settings set discover-interface-list=none\r\
    \n/file remove disableDiscovery.rsc\r\
    \n/system script remove disableDiscovery"