package java_.nio.buffer.zero_copy;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

/**
 * @author Sergey Kuptsov
 * 4 Mb file
 * Benchmark                                                      Mode  Cnt        Score         Error  Units
StreamCopyBuffersBench.copyFileWithBufferedReaderBuffer        avgt   10    59066,319 ±   41948,307  us/op
StreamCopyBuffersBench.copyFileWithBufferedReaderByOneByte     avgt   10  4403774,947 ± 1897770,490  us/op
StreamCopyBuffersBench.copyFileWithBufferedStreamOneByOneByte  avgt   10  3715690,226 ±  820259,247  us/op
StreamCopyBuffersBench.copyFileWithFileChannelDirectBuffer     avgt   10    59154,232 ±    4075,458  us/op
StreamCopyBuffersBench.copyFileWithFileChannelHeapByteBuffer   avgt   10    60038,580 ±    2089,672  us/op
StreamCopyBuffersBench.copyFileWithZeroCopyFileChannelCopyTo   avgt   10      271,429 ±     142,407  us/op
 */
@State(value = Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
public class StreamCopyBuffersBench {

    private static final String INPUT_FILE = "/presentInput2.txt";
    private static final String INPUT_FILE_PATH = "/Users/kuptservol/work/code/algorythms/src/main/resources/presentInput2.txt";

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void copyFileWithBufferedStreamOneByOneByte() throws IOException {
        File file = Files.createTempFile("tmp", "tmp").toFile();

        try (BufferedInputStream fileInput =
                     new BufferedInputStream(StreamCopyBuffersBench.class.getResourceAsStream(INPUT_FILE));
             FileOutputStream fileOutputStream = new FileOutputStream(file))
        {
            int next;
            while ((next = fileInput.read()) != -1) {
                fileOutputStream.write(next);
            }
        } finally {
            Files.delete(file.toPath());
        }
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void copyFileWithZeroCopyFileChannelCopyTo() throws IOException {
        File file = Files.createTempFile("tmp", "tmp").toFile();
        long length = file.length();

        try (
                FileChannel inputFileChannel = new FileInputStream(INPUT_FILE_PATH).getChannel();
                FileChannel fileOutputChannel = new FileOutputStream(file).getChannel();
        )
        {
            inputFileChannel.transferTo(0, length, fileOutputChannel);
        } finally {
            Files.delete(file.toPath());
        }
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void copyFileWithFileChannelHeapByteBuffer() throws IOException {
        File file = Files.createTempFile("tmp", "tmp").toFile();

        ByteBuffer buffer = ByteBuffer.allocate(100);

        try (
                FileChannel inputFileChannel = new FileInputStream(INPUT_FILE_PATH).getChannel();
                FileChannel fileOutputChannel = new FileOutputStream(file).getChannel();
        )
        {

            while (inputFileChannel.read(buffer) != -1) {
                buffer.flip();
                fileOutputChannel.write(buffer);
                buffer.clear();
            }
        } finally {
            Files.delete(file.toPath());
        }
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void copyFileWithFileChannelDirectBuffer() throws IOException {
        File file = Files.createTempFile("tmp", "tmp").toFile();

        ByteBuffer buffer = ByteBuffer.allocateDirect(100);

        try (
                FileChannel inputFileChannel = new FileInputStream(INPUT_FILE_PATH).getChannel();
                FileChannel fileOutputChannel = new FileOutputStream(file).getChannel();
        )
        {

            while (inputFileChannel.read(buffer) != -1) {
                buffer.flip();
                fileOutputChannel.write(buffer);
                buffer.clear();
            }
        } finally {
            Files.delete(file.toPath());
        }
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void copyFileWithBufferedReaderByOneByte() throws IOException {
        File file = Files.createTempFile("tmp", "tmp").toFile();

        try (BufferedReader fileInput =
                     new BufferedReader(new InputStreamReader(StreamCopyBuffersBench.class.getResourceAsStream(INPUT_FILE)));
             FileOutputStream fileOutputStream = new FileOutputStream(file))
        {
            int next;
            while ((next = fileInput.read()) != -1) {
                fileOutputStream.write(next);
            }
        } finally {
            Files.delete(file.toPath());
        }
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void copyFileWithBufferedReaderBuffer() throws IOException {
        File file = Files.createTempFile("tmp", "tmp").toFile();

        try (BufferedReader fileInput =
                     new BufferedReader(new InputStreamReader(StreamCopyBuffersBench.class.getResourceAsStream(INPUT_FILE)));
             FileOutputStream fileOutputStream = new FileOutputStream(file))
        {
            int next;
            char[] readBuff = new char[100];
            while ((next = fileInput.read(readBuff)) != -1) {
                fileOutputStream.write(next);
            }
        } finally {
            Files.delete(file.toPath());
        }
    }
}
