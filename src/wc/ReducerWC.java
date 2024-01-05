package wc;
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.commons.cli.ParseException;
public class ReducerWC extends MapReduceBase implements Reducer<Text,IntWritable,Text,IntWritable> {

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
			output.collect(key, new IntWritable(count));
	}

}
