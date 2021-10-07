package io.github.glowman554.test;

import java.io.IOException;

import io.github.glowman554.libcsv.CsvDoc;
import io.github.glowman554.twitch.TwitchApi;
import io.github.glowman554.utils.ArgParser;
import io.github.glowman554.utils.FileUtils;
import net.shadew.json.JsonSyntaxException;

public class Main
{
	public static void main(String[] args) throws IOException, JsonSyntaxException
	{
		// String[] test_args = {
		// 	"--user=amouranth",
		// 	"--save-as=test.txt",
		// 	"--csv=all_revenues.csv"
		// };

		ArgParser parser = new ArgParser(args);
		parser.parse();

		String user = "";
		String save_as = "";
		String csv = parser.consume_option("--csv", "all_revenues.csv");;

		try
		{
			user = parser.consume_option("--user");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("Error: " + e.getMessage());
			System.exit(-1);
		}

		try
		{
			save_as = parser.consume_option("--save-as");
		}
		catch (IllegalArgumentException e)
		{
			save_as = "";
		}
		
		System.out.println("Reading csv file...");
		CsvDoc doc = new CsvDoc(FileUtils.readFile(csv));
		
		System.out.println("Parsing csv file...");
		doc.parse();
		
		System.out.println("Resolving twitch user " + user);
		String user_id = TwitchApi.get_user_id(user);
		
		System.out.println("Resolved twitch user " + user + " to " + user_id);
		
		System.out.println("Searching csv...");
		int row = doc.find_row(user_id, 0);
		
		System.out.println(doc.toString(row) + "\n");
		
		if (!save_as.equals(""))
		{
			FileUtils.writeFile(save_as, doc.toString(row));
			System.out.println("Saved result as " + save_as);
		}

		// System.out.println("Hello World!");

		// CsvDoc doc = new CsvDoc(FileUtils.readFile("all_revenues.csv"));

		// doc.parse();

		// String user = TwitchApi.get_user_id("amouranth");
		// int row = doc.find_row(user, 0);
		// System.out.println(row);
		// System.out.println(doc.toString(row));
		// FileUtils.writeFile("test.txt", doc.toString(row));

		//FileUtils.writeFile("test.txt", doc.toString(10));

		//System.out.println(doc.toString());
		//System.out.println(TwitchApi.get_user_id("glowman434"));
		//FileUtils.writeFile("all_revenues.csv.txt", doc.toString());
		//System.out.println(doc.serialize());
		//System.out.println(doc.getCell(10, 1));
		//System.out.println(doc.find(doc.getCell(10, 1), 1)[0]);

		// String[][] test = doc.find_all("mündlich", 1);

		// for (int i = 0; i < test.length; i++)
		// {
		// 	for (int j = 0; j < test[i].length; j++)
		// 	{
		// 		System.out.print(test[i][j] + " ");
		// 	}
		// 	System.out.println();
		// }
	}
}
