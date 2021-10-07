package io.github.glowman554.utils;

import java.util.ArrayList;

public class ArgParser
{
	class ArgParserNode
	{
		public String option;
		public String value;
		
		public ArgParserNode(String option, String value)
		{
			this.option = option;
			this.value = value;
		}

		public ArgParserNode(String option)
		{
			this.option = option;
		}

		@Override
		public String toString()
		{
			return String.format("ArgParseNode[%s=%s]", option, value);
		}
	}

	ArrayList<ArgParserNode> args_list = new ArrayList<>();
	
	private String[] args;

	public ArgParser(String[] args)
	{
		this.args = args;
	}

	public void parse()
	{
		for (int i = 0; i < args.length; i++)
		{
			if (args[i].startsWith("-"))
			{
				String arg = args[i];
				if (arg.contains("="))
				{
					String[] split = arg.split("=");
					args_list.add(new ArgParserNode(split[0], split[1]));
				}
				else
				{
					args_list.add(new ArgParserNode(arg));
				}
			}
			else
			{
				args_list.add(new ArgParserNode(args[i]));
			}
		}
	}

	public String consume_option(String option)
	{
		for (ArgParserNode arg : args_list)
		{
			if (arg.option.equals(option))
			{
				args_list.remove(arg);
				return arg.value;
			}
		}

		throw new IllegalArgumentException("Option " + option + " not found");
	}

	public String consume_option(String option, String default_value)
	{
		try
		{
			String value = consume_option(option);
			if (value == null)
			{
				return default_value;
			}
			else
			{
				return value;
			}
		}
		catch (IllegalArgumentException e)
		{
			return default_value;
		}
	}

	public boolean is_option(String option)
	{
		for (ArgParserNode arg : args_list)
		{
			if (arg.option.equals(option))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (ArgParserNode arg : args_list)
		{
			sb.append(arg.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}