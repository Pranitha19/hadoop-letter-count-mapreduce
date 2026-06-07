/* ........
Pranitha
Avula
 .......... */
 import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class LetterCount {

    public static void main(String[] args)
            throws Exception {

        if (args.length != 2) {
            System.err.println("Usage: LetterCount <input path> <output path>");
            System.exit(-1);
        }

        Job job = Job.getInstance();

        job.setJarByClass(LetterCount.class);
        job.setJobName("Letter Count");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(LetterMapper.class);
        job.setReducerClass(LetterReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setNumReduceTasks(1);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class LetterMapper
            extends Mapper<LongWritable, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {

            String line = value.toString().toUpperCase();

            for (int i = 0; i < line.length(); i++) {

                char ch = line.charAt(i);

                if (Character.isLetter(ch)) {

                    context.write(
                            new Text(String.valueOf(ch)),
                            one
                    );
                }
            }
        }
    }

    public static class LetterReducer
            extends Reducer<Text, IntWritable, Text, IntWritable> {

        private int totalLetters = 0;

        public void reduce(Text key,
                           Iterable<IntWritable> values,
                           Context context)
                throws IOException, InterruptedException {

            int count = 0;

            for (IntWritable value : values) {
                count += value.get();
            }

            totalLetters += count;

            context.write(key, new IntWritable(count));
        }

        protected void cleanup(Context context)
                throws IOException, InterruptedException {

            context.write(
                    new Text("total"),
                    new IntWritable(totalLetters)
            );
        }
    }
}
