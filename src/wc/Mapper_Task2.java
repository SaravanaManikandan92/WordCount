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
import org.apache.log4j.Logger;

public class Mapper_Task2 extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

	// logger declaration and initialization
	static final Logger logger = Logger.getLogger(Mapper_Task2.class);

	// Mapper's map functionality where we segaragate the content of file based on
	// their existence
	// for instance if a file contains a b c a then the following mapper would
	// produce an output {a=1,b=1,c=1,a=1}
	// this logic is specifically for word count scenarios and will differ based on
	// different needs
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
