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
import org.apache.log4j.Logger;
import org.apache.commons.cli.ParseException;
public class Combiner extends MapReduceBase implements Reducer<Text,IntWritable,Text,IntWritable> {

	// logger declaration and initialization
			static final Logger logger = Logger.getLogger(Combiner.class);
	
	// In mapper-combining without preserving the state - default combining techniques used 
	//before feeding the reducer 
	private IntWritable totalWords=new IntWritable();
	@Override
	public void reduce(Text key, Iterator<IntWritable> value, OutputCollector<Text, IntWritable> output, Reporter rep)
			throws IOException {
		
		// TODO Auto-generated method stub
		int count =0;
		
		while(value.hasNext())
		{
			//code which groups value  by their respective  key 
			IntWritable i= value.next();
			count+=i.get();
		}
		totalWords.set(count);
		//key with consolidated value - for instance {a=1,a=1} i converted to {a=2}
	    output.collect(key,totalWords);
	}

}
