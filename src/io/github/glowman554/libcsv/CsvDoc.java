package io.github.glowman554.libcsv;

import java.util.ArrayList;

public class CsvDoc
{
	public String document;

	public ArrayList<ArrayList<String>> parsed_document;

	public CsvDoc(String document)
	{
		this.document = document;
	}

	public void parse()
	{
		parsed_document = new ArrayList<ArrayList<String>>();

		String[] lines = document.split("\n");

		String line1 = lines[0];

		for (int i = 0; i < line1.split(",").length; i++)
		{
			parsed_document.add(new ArrayList<String>());
		}

		for (String line : lines)
		{
			String[] cells = line.split(",");

			for (int i = 0; i < cells.length; i++)
			{
				parsed_document.get(i).add(cells[i]);
			}
		}
	}

	public String serialize()
	{
		String serialized = "";

		for (int i = 0; i < parsed_document.get(0).size(); i++)
		{
			for (int j = 0; j < parsed_document.size(); j++)
			{
				String cell = parsed_document.get(j).get(i);
				serialized += cell + ",";
			}

			serialized = serialized.substring(0, serialized.length() - 1);
			serialized += "\n";
		}

		return serialized;
	}

	public String getCell(int row, int column)
	{
		return parsed_document.get(column).get(row);
	}

	public void setCell(int row, int column, String value)
	{
		parsed_document.get(column).set(row, value);
	}

	public int getRowCount()
	{
		return parsed_document.get(0).size();
	}

	public int getColumnCount()
	{
		return parsed_document.size();
	}

	public String[] find(String search, int column)
	{
		ArrayList<String> found = new ArrayList<String>();

		for (int i = 0; i < parsed_document.get(column).size(); i++)
		{
			if (parsed_document.get(column).get(i).equals(search))
			{
				for (int j = 0; j < parsed_document.size(); j++)
				{
					for (int k = 0; k < parsed_document.get(j).size(); k++)
					{
						if (k == column)
						{
							found.add(parsed_document.get(j).get(i));
						}
					}
				}
				break;
			}
		}

		return found.toArray(new String[found.size()]);	
	}

	public int find_row(String search, int column)
	{
		for (int i = 0; i < parsed_document.get(column).size(); i++)
		{
			if (parsed_document.get(column).get(i).equals(search))
			{
				return i;
			}
		}

		return -1;
	}

	public String[][] find_all(String search, int column)
	{
		ArrayList<String[]> found = new ArrayList<String[]>();

		for (int i = 0; i < parsed_document.get(column).size(); i++)
		{
			if (parsed_document.get(column).get(i).equals(search))
			{
				ArrayList<String> row = new ArrayList<String>();
				System.out.println(parsed_document.get(column).get(i));
				// add row
				for (int j = 0; j < parsed_document.size(); j++)
				{
					for (int k = 0; k < parsed_document.get(j).size(); k++)
					{
						if (k == column)
						{
							row.add(parsed_document.get(j).get(i));
						}
					}
				}
				found.add(row.toArray(new String[row.size()]));
			}
		}

		return found.toArray(new String[found.size()][]);
	}

	@Override
	public String toString()
	{
		String ret = "";

		int[] max_lengths = new int[parsed_document.size()];

		boolean clip_output = parsed_document.get(0).size() > 1000;
		int clip_length = 1000;

		for (int i = 0; i < parsed_document.size(); i++)
		{
			int max = 0;

			if (!clip_output)
			{
				for (String cell : parsed_document.get(i))
				{
					if (cell.length() > max)
					{
						max = cell.length();
					}
				}
			}
			else
			{
				for (int x = 0; x < clip_length; x++)
				{
					String cell = parsed_document.get(i).get(x);
					if (cell.length() > max)
					{
						max = cell.length();
					}
				}
			}

			max_lengths[i] = ++max;
		}

		ret += "|";

		for (int j = 0; j < parsed_document.size(); j++)
		{
			String header = "";
	
			while (header.length() != max_lengths[j])
			{
				header += "-";
			}
	
			ret += header + "|";
		}

		ret += "\n";

		for (int i = 0; i < (clip_output ? clip_length : parsed_document.get(0).size()); i++)
		{
			ret += "|";

			if (i == 1)
			{
				for (int j = 0; j < parsed_document.size(); j++)
				{
					String header = "";
	
					while (header.length() != max_lengths[j])
					{
						header += "-";
					}
	
					ret += header + "|";
				}

				ret += "\n|";
			}

			for (int j = 0; j < parsed_document.size(); j++)
			{
				String cell = parsed_document.get(j).get(i);

				while (cell.length() != max_lengths[j])
				{
					cell += " ";
				}

				ret += cell + "|";
			}

			ret += "\n";
		}

		ret += "|";

		for (int j = 0; j < parsed_document.size(); j++)
		{
			String header = "";
	
			while (header.length() != max_lengths[j])
			{
				header += "-";
			}
	
			ret += header + "|";
		}

		ret += "\n";

		if (clip_output)
		{
			ret += "\nClipped " + (parsed_document.get(0).size() - clip_length) + " rows!";
		}

		return ret;
	}

	public String toString(int row)
		{
		String ret = "";

		int[] max_lengths = new int[parsed_document.size()];

		for (int i = 0; i < parsed_document.size(); i++)
		{
			int max = 0;

			for (String cell : parsed_document.get(i))
			{
				if (cell.length() > max)
				{
					max = cell.length();
				}
			}

			max_lengths[i] = ++max;
		}

		ret += "|";

		for (int j = 0; j < parsed_document.size(); j++)
		{
			String header = "";
	
			while (header.length() != max_lengths[j])
			{
				header += "-";
			}
	
			ret += header + "|";
		}

		ret += "\n|";

		for (int j = 0; j < parsed_document.size(); j++)
		{
			String cell = parsed_document.get(j).get(0);

			while (cell.length() != max_lengths[j])
			{
				cell += " ";
			}

			ret += cell + "|";
		}

		ret += "\n|";

		for (int j = 0; j < parsed_document.size(); j++)
		{
			String cell = parsed_document.get(j).get(row);

			while (cell.length() != max_lengths[j])
			{
				cell += " ";
			}

			ret += cell + "|";
		}

		ret += "\n";

		ret += "|";

		for (int j = 0; j < parsed_document.size(); j++)
		{
			String header = "";
	
			while (header.length() != max_lengths[j])
			{
				header += "-";
			}
	
			ret += header + "|";
		}

		ret += "\n";

		return ret;
	}

}
