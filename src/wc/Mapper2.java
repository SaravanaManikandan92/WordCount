package wc;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
public class Mapper2 extends MapReduceBase implements Mapper<LongWritable,Text,Text,IntWritable> {

	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter rep)
			throws IOException {
		// TODO Auto-generated method stub

		System.out.println("Mapper3");
		String line=value.toString();
		for(String word : line.split(" "))
		{

			int val=word.toString().length();
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
