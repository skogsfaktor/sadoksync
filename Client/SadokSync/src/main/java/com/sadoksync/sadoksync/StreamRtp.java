package com.sadoksync.sadoksync;
/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009, 2010, 2011, 2012, 2013, 2014, 2015 Caprica Software Limited.
 */
import com.sun.jna.NativeLibrary;
import java.io.File;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

/**
 * An example of how to stream a media file using RTP.
 * <p>
 * The client specifies an MRL of <code>rtp://@230.0.0.1:5555</code>
 */


public class StreamRtp {

    public static void main(String[] args) throws Exception {
        StringBuilder filePath = new StringBuilder();
        filePath.append(new File("").getAbsolutePath());
        filePath.append("\\target\\VLC64");
        System.out.println(filePath);
        NativeLibrary.addSearchPath("libvlc", filePath.toString());
        
        
        String media = "C:\\Users\\Skogsfaktor\\Downloads\\Video\\Risitas.mp4";
        String options = formatRtpStream("127.0.0.1", 5555);
        System.out.println("Streaming '" + media + "' to '" + options + "'");
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(args);
        HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
        mediaPlayer.playMedia(media,
                options,
                ":no-sout-rtp-sap",
                ":no-sout-standard-sap",
                ":sout-all",
                ":sout-keep"
        );
// Don't exit
        Thread.currentThread().join();
    }

    private static String formatRtpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{dst=");
        sb.append(serverAddress);
        sb.append(",port=");
        sb.append(serverPort);
        sb.append(",mux=ts}");
        return sb.toString();
    }
}
