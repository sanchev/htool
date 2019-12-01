package anonymous.terminal

import anonymous.base.Device
import anonymous.base.Host
import anonymous.base.Service
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class MikroTikTerminator(private val host: Host) : Runnable {

    private var ip: String? = null
    private var username: String? = null
    private var password: String? = null

    override fun run() {
        ip = host.ip
        username = host.login
        password = host.password
        for (device in host.deviceList) {
            if ("MikroTik" == device.vendor) {
                var apiPort = MikroTikTerminal.DEFAULT_PORT
                var telnetPort = TelnetTerminal.DEFAULT_PORT
                var ftpPort = FTPTerminal.DEFAULT_PORT
                if (!device.serviceList.isEmpty()) {
                    for (service in device.serviceList) {
                        if ("api" == service.name)
                            apiPort = service.port
                        if ("telnet" == service.name)
                            telnetPort = service.port
                        if ("ftp" == service.name)
                            ftpPort = service.port
                    }
                }
                val mikroTikTerminal = MikroTikTerminal()
                val telnetTerminal = TelnetTerminal(LOGIN_PATTERN, PASSWORD_PATTERN, CMD_PATTERN)
                val ftpTerminal = FTPTerminal()

                val connected = connect(mikroTikTerminal, apiPort, telnetTerminal, telnetPort, ftpTerminal, ftpPort)

                if (connected) {
                    LOGGER.info("Logged on " + ip!!)

                    //do something

                    if (disconnect(mikroTikTerminal, telnetTerminal, ftpTerminal)) {
                        LOGGER.info("Logout from " + ip!!)
                    }
                } else {
                    LOGGER.info("NOT logged on $ip. Try again.")
                }
            }
        }
    }

    private fun connect(mikroTikTerminal: MikroTikTerminal, apiPort: Int, telnetTerminal: TelnetTerminal, telnetPort: Int, ftpTerminal: FTPTerminal, ftpPort: Int): Boolean {
        var connected = mikroTikTerminal.connect(ip, apiPort, username, password)
        if (!connected) {
            connected = telnetTerminal.connect(ip, telnetPort, username, password)
            if (connected) {
                telnetTerminal.execute("ip service enable api")
                connected = mikroTikTerminal.connect(ip, apiPort, username, password)
            } else {
                return false
            }
        }
        mikroTikTerminal.execute("/ip/service/enable numbers=ftp")
        ftpTerminal.connect(ip, ftpPort, username, password)
        return connected
    }

    private fun disconnect(mikroTikTerminal: MikroTikTerminal, telnetTerminal: TelnetTerminal, ftpTerminal: FTPTerminal): Boolean {
        return mikroTikTerminal.disconnect() && telnetTerminal.disconnect() && ftpTerminal.disconnect()
    }

    companion object {
        private val LOGGER = LogManager.getLogger(MikroTikTerminator::class.java!!.getName())

        private val LOGIN_PATTERN = "Login: "
        private val PASSWORD_PATTERN = "Password: "
        private val CMD_PATTERN = "\\[.+@.+\\][^>]+[>]+ "

        private val LOCAL_PATH = "src/main/resources/"
    }
}