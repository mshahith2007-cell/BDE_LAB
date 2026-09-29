import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WeatherAnalysis {

    public static class TempMapper
            extends Mapper<Object, Text, Text, IntWritable> {

        private Text year = new Text();
        private IntWritable temperature = new IntWritable();

        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {

            String line = value.toString();

            String yearStr = line.substring(0, 4);
            String tempStr = line.substring(14, 18).trim();

            try {
                int temp = Integer.parseInt(tempStr);

                year.set(yearStr);
                temperature.set(temp);

                context.write(year, temperature);

            } catch (NumberFormatException e) {
                // Ignore invalid temperature lines
            }
        }
    }

    public static class MaxTempReducer
            extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context)
                throws IOException, InterruptedException {

            int maxTemp = Integer.MIN_VALUE;

            for (IntWritable val : values) {
                maxTemp = Math.max(maxTemp, val.get());
            }

            result.set(maxTemp);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "weather analysis");

        job.setJarByClass(WeatherAnalysis.class);
        job.setMapperClass(TempMapper.class);
        job.setReducerClass(MaxTempReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}