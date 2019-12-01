/system script add name=metarouter\
 source="/interface ethernet set name=ether numbers=0\r\
    \n/interface wireless set name=wlan numbers=0\r\
    \n/interface pppoe-client set name=pppoe-out numbers=0\r\
    \n/interface bridge add name=bridge\r\
    \n/interface bridge port add bridge=bridge interface=ether\r\
    \n\r\
    \n/ip pool set name=dhcp-pool numbers=0\r\
    \n/ip dhcp-server set name=dhcp numbers=0\r\
    \n/ip dhcp-server set interface=bridge numbers=0\r\
    \n/ip address set interface=bridge numbers=0\r\
    \n\r\
    \n/metarouter import-image enabled=no memory-size=16 file-name=openwrt-mr-mips-rootfs-36088.tar.gz\r\
    \n/metarouter set name=mr numbers=0\r\
    \n/metarouter set disk-size=32768 numbers=0\r\
    \n/metarouter interface add virtual-machine=mr type=dynamic dynamic-bridge=bridge\r\
    \n/metarouter enable numbers=0\r\
    \n/file remove metarouter.rsc\r\
    \n/system script remove metarouter"