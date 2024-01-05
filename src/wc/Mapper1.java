package wc;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.log4j.Logger;
public class Mapper1 extends MapReduceBase implements Mapper<LongWritable,Text,Text,IntWritable> {
	
	
	static final Logger logger = Logger.getLogger(Mapper1.class);
	private static String separator;
	private static String commonSeparator;
	private static String FILE_TAG="F2";
	public void setup(Context context)
	{
		System.out.println("Saravana setup");
		
	}
	
	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter rep)
			throws IOException {
		//logger.info(" /n/r Saravana Mapper 1 invoked /n/r ");
		System.out.println("Mapper1");
		// TODO Auto-generated method stub
		  StringTokenizer st = new StringTokenizer(value.toString());
		  while (st.hasMoreTokens()) {
				int val=st.nextToken().trim().toString().length();
				switch(val)
				{
				case 1:
				case 2:
				case 3:
				case 4:
			    output.collect(new Text("shortWords"), new IntWritable(1));
			    break;
				case 5:
				case 6:
				case 7:
				output.collect(new Text("mediumWords"), new IntWritable(1));
					break;
				case 8:
				case 9:
				case 10:
					output.collect(new Text("longWords"), new IntWritable(1));
				default:
					output.collect(new Text("extraLongWords"), new IntWritable(1));
					break;
				
				}

				
			
		      
		      
		  }
		
		
		
	}

}
