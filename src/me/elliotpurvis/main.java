package me.elliotpurvis;

/**
 * Created by elliot on 31/07/16.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;


import com.jcraft.jsch.*;


public class main {


    public static void main(String[] args) {

        try {

            BufferedReader br = null;


            JSch jsch = new JSch();


            br = new BufferedReader(new InputStreamReader(System.in));
            String hostname, user, port;
            // default to port 22
            port = Integer.toString(22);

            System.out.println("Hostname: ");
            String hostInput = br.readLine();


            // host:port, if port is not entered use default.
            if (hostInput.contains(":")) {
                String[] hostInputArray = hostInput.split(":");
                hostname = hostInputArray[0];
                port = hostInputArray[1];
            } else {
                hostname = hostInput;
            }


            // user login
            System.out.println("User: ");
            user = br.readLine();


            Session session = jsch.getSession(user, hostname, Integer.parseInt(port));


            // Disable key checking
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // Get auth
            char[] passwd = System.console().readPassword("Password: ");
            session.setPassword(new String(passwd));


            System.out.println("Attempting to connect to " + hostname + " on port " + port + "....");
            session.connect();


            Channel channel = session.openChannel("shell");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect();
            while(true){
                if(channel.isClosed() || !(session.isConnected())){
                    System.out.println("Session lost");

                    session.disconnect();
                    channel.disconnect();
                    // Neither channel.dis or session.dis throw exceptions if already disconnected.

                }
            }

        } catch (Exception e ){
            e.printStackTrace();
        }
    }


}