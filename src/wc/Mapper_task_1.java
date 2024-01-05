package wc;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class Mapper_task_1 extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

	private static String separator;
	private static String commonSeparator;
	private static String FILE_TAG = "F2";

	public void setup(Context context) {
		Configuration configuration = context.getConfiguration();
		// Retrieving the file separator from context for file2.
		separator = configuration.get("Separator.Mapper1");
		// Retrieving the file separator from context for writing the data to reducer.
		commonSeparator = configuration.get("Separator.Reducer");
	}

	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter rep)
			throws IOException {
		// TODO Auto-generated method stub
		StringTokenizer st = new StringTokenizer(value.toString());
		while (st.hasMoreTokens()) {
			output.collect(new Text(st.nextToken().trim()), new IntWritable(1));

		}
	}

}
