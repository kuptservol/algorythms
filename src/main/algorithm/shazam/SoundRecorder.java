package algorithm.shazam;

/**
 * @author Sergey Kuptsov
 */
public class SoundRecorder {

//    private static volatile boolean running = true;
//
//    public static void main(String[] args) throws LineUnavailableException, IOException {
//
//        AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
//        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
//        final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
//        line.open(format);
//        line.start();
//
//
//        BufferedWriter out = Files.newBufferedWriter(Paths.get("/Users/kuptservol/work/code/algorythms/sound"));
//        byte[] buffer = new byte[line.getBufferSize() / 5];
//
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            running = false;
//            line.stop();
//
//            try {
//                line.drain();
//                out.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }));
//
//        try {
//            while (running) {
//                int count = line.read(buffer, 0, buffer.length);
//                if (count > 0) {
//                    out.write(buffer, 0, count);
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("I/O problems: " + e);
//            System.exit(-1);
//        } finally {
//            line.stop();
//            out.close();
//        }
//    }
}
