/system script add name=time source="/system clock set time-zone-autodetect=no\r\
    \n/system clock set time-zone-name=Europe/Kiev\r\
    \n/system ntp client set enabled=no\r\
    \n/system ntp client set secondary-ntp=0.0.0.0\r\
    \n/system ntp client set primary-ntp=0.0.0.0\r\
    \n/system ntp client set server-dns-names=time1.google.com,time2.google.com,time3.google.com,time4.google.com\r\
    \n/system ntp client set enabled=yes\r\
    \n/file remove time.rsc\r\
    \n/system script remove time"
