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

public class MapperWC_2 extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

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

			switch (val) {
			case 1:
			case 2:
			case 3:
			case 4:
				if (count.containsKey("shortWords")) {
					int sum = (int) count.get("shortWords") + 1;
					count.put("shortWords", sum);
				} else {
					count.put("shortWords", 1);
				}

				break;
			case 5:
			case 6:
			case 7:
				if (count.containsKey("mediumWords")) {
					int sum = (int) count.get("mediumWords") + 1;
					count.put("mediumWords", sum);
				} else {
					count.put("mediumWords", 1);
				}

				break;
			case 8:
			case 9:
			case 10:
				if (count.containsKey("longWords")) {
					int sum = (int) count.get("longWords") + 1;
					count.put("longWords", sum);
				} else {
					count.put("longWords", 1);
				}
			default:
				if (count.containsKey("extraLongWords")) {
					int sum = (int) count.get("extraLongWords") + 1;
					count.put("extraLongWords", sum);
				} else {
					count.put("extraLongWords", 1);
				}
				break;

			}

		}
		actualOutput=output;
		
		
		 
	}
	
	public void close()
	        throws IOException{try {
				MapperWC_2.cleanup();
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
