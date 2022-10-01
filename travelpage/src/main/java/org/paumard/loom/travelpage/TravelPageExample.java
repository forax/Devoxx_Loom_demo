package org.paumard.loom.travelpage;

import org.paumard.loom.travelpage.model.Travel;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

public class TravelPageExample {

    // Changing the following to SECONDS will give you enough time to
    // dump the threads in a JSON file, with this command:
    // jcmd <the displayed pid value> Thread.dump_to_file  -format=json <your json file>
    public static final ChronoUnit CHRONO_UNIT = ChronoUnit.MILLIS;


    // --enable-preview --add-modules jdk.incubator.concurrent --add-exports java.base/jdk.internal.vm=ALL-UNNAMED

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var pid = ProcessHandle.current().pid();
        System.out.println("pid = " + pid);

        System.out.println("Travel page = " + Travel.readTravelPage());
    }
}
