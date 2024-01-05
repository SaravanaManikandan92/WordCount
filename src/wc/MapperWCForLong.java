package wc;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

//Text,IntWritable
public class MapperWCForLong extends MapReduceBase implements Mapper<Text,IntWritable, Text, IntWritable> {

	@Override
	public void map(Text key, IntWritable value, OutputCollector<Text, IntWritable> output, Reporter rep)
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
				output.collect(new Text(word), new IntWritable(1));
				break;
			case 5:
			case 6:
			case 7:
				output.collect(new Text(word), new IntWritable(1));
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
