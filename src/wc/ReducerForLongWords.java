package wc;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class ReducerForLongWords extends MapReduceBase implements Reducer<Text,IntWritable,Text,IntWritable> {

	@Override
	public void reduce(Text key, Iterator<IntWritable> value, OutputCollector<Text, IntWritable> output, Reporter rep)
			throws IOException {
		// TODO Auto-generated method stub
		int count =0;
		while(value.hasNext())
		{
			IntWritable i= value.next();
			count+=i.get();
		}
		//if(key.toString().equalsIgnoreCase("longWords")||key.toString().equalsIgnoreCase("extraLongWords"))
		output.collect(key, new IntWritable(count));
	}

}
