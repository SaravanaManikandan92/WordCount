package wc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

public class MapperWC_1_words extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

	private static String separator;
	private static String commonSeparator;
	private static String FILE_TAG = "F2";
    private static Map count=new HashMap<String, Integer>();
	public void setup(Context context) {}


	static OutputCollector<Text, IntWritable> actualOutput;
	// Object key, Text value, Context context

	// OutputCollector<Text, IntWritable> output
	// static OutputCollector<Text, IntWritable> output;
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter rep)
			throws IOException {
		// TODO Auto-generated method stub
		
		StringTokenizer st = new StringTokenizer(value.toString());
		while (st.hasMoreTokens()) {
			String s=st.nextToken().trim().toString();
			if (count.containsKey(s)) {
				int sum = (int) count.get(s) + 1;
				count.put(s, sum);
			} else {
				count.put(s, 1);
			}

		}
		
		actualOutput = output;

	}

	public void close() throws IOException {
		try {
			System.out.println("Close");
			MapperWC_1_words.cleanup();
			 count=new HashMap<String, Integer>();
		} catch (IOException e) { // TODO
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void cleanup() throws IOException, InterruptedException {
		Iterator<Map.Entry<Integer, Integer>> temp = count.entrySet().iterator();
		System.out.println("Count map 1 >>>  " + count);
		while (temp.hasNext()) {
			Map.Entry<Integer, Integer> entry = temp.next();
			String keyVal = entry.getKey() + "";
			Integer countVal = entry.getValue();
			System.out.println("Counttt map "+ countVal);
			actualOutput.collect(new Text(keyVal), new IntWritable(countVal));
		}
		//count=new HashMap<String, Integer>();
		System.out.println("Count actualOutput map 1 close >>>  " + actualOutput);
	}

}
