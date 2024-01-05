package wc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class MapperWC_3_words extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

	private static String separator;
	private static String commonSeparator;
	private static String FILE_TAG = "F2";

	public void setup(Context context) {
		Configuration configuration = context.getConfiguration();
		// Retrieving the file separator from context for file2.
		separator = configuration.get("Separator.MapperWC_3");
		// Retrieving the file separator from context for writing the data to reducer.
		commonSeparator = configuration.get("Separator.Reducer");
	}

	static Map count = new HashMap<String, Integer>();
	static OutputCollector<Text, IntWritable> actualOutput;
	// Object key, Text value, Context context

	// OutputCollector<Text, IntWritable> output
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter rep)
			throws IOException {
		// TODO Auto-generated method stub
		String line = value.toString();
		for (String word : line.split(" ")) {
			int val = word.toString().length();
			if (count.containsKey(word)) {
				int sum = (int) count.get(word) + 1;
				count.put(word, sum);
			} else {
				count.put(word, 1);
			}
		}
		actualOutput = output;

	}

	public void close() throws IOException {
		try {
			MapperWC_3_words.cleanup();
		} catch (IOException e) { // TODO
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void cleanup() throws IOException, InterruptedException {
		Iterator<Map.Entry<Integer, Integer>> temp = count.entrySet().iterator();
		while (temp.hasNext()) {
			Map.Entry<Integer, Integer> entry = temp.next();
			String keyVal = entry.getKey() + "";
			Integer countVal = entry.getValue();
			actualOutput.collect(new Text(keyVal), new IntWritable(countVal));
			// context.write(new Text(keyVal), new IntWritable(countVal));
			// context.write(new Text(keyVal), new IntWritable(countVal));
		}
	}

}
