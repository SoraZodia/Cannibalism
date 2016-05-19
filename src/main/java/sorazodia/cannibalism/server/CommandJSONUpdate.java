package sorazodia.cannibalism.server;

public class CommandJSONUpdate
{
//	public void update()
//	{
//		File oldJSON = new File(filePath);
//		File tempJSON = new File(dirPath + "\\json.temp");
//		BufferedWriter writer;
//		BufferedReader reader;
//		StringBuilder newEntry;
//		String line;
//
//		log.info("[Cannibalism] Updating JSON to include new entry");
//
//		try
//		{
//
//			writer = new BufferedWriter(new FileWriter(tempJSON));
//			reader = new BufferedReader(new FileReader(oldJSON));
//
//			line = reader.readLine();
//			newEntry = new StringBuilder();
//
//			if (line.length() > 1)
//			{
//				for (int x = 1; x < line.length(); x++)
//					newEntry.append(line.charAt(x));
//
//				newEntry.append("\n");
//				line = "[";
//			}
//			if (!(!isWildCardEntry("minecraft:Sheep") || !entityMap.containsKey("minecraft:Sheep")))
//			{
//				writer.write(line + "\n");
//				writer.write("{\n"
//				        + "\"entityID\":\"Sheep\","
//						+ "\n\"modID\":\"minecraft\","
//				        + "\n\"drops\":\"minecraft:wool,minecraft:mutton\","
//						+ "\n\"minDamage\":\"2.5\","
//				        + "\n\"maxDamage\":\"3.0\""
//						+ "\n}");
//				line = "}";
//			}
//			if (!(!isWildCardEntry("minecraft:Witch") || !entityMap.containsKey("minecraft:Witch")))
//			{
//				if (line.equals("}"))
//				   line = ",";
//	
//				//line would be [ if the sheep entity was not added
//				writer.write(line + "\n");
//				writer.write("{\n"
//				        + "\"entityID\":\"Witch\","
//						+ "\n\"modID\":\"minecraft\","
//				        + "\n\"drops\":\"cannibalism:witchFlesh\","
//						+ "\n\"minDamage\":\"5\","
//				        + "\n\"maxDamage\":\"6\""
//						+ "\n}");
//			}
//			else
//				log.info("[Cannibalism] Update not needed, new entry is already there");
//
//			//Checks if this is not the only entity, and if is then don't add a comma to avoid a parsing error 
//			if ((line = reader.readLine()).equals("]"))
//				writer.write("\n" + line);
//			else
//				writer.write(",\n");
//
//			writer.write(newEntry.toString());
//			
//			while ((line = reader.readLine()) != null)
//				writer.write(line + "\n");
//
//			reader.close();
//			writer.close();
//		}
//		catch (IOException io)
//		{
//			log.info("[Cannibalism] Unable to update JSON!");
//			io.printStackTrace();
//		}
//
//		oldJSON.delete();
//
//		if (!tempJSON.renameTo(oldJSON))
//			log.info("[Cannibalism] Unable to update JSON!");
//		else
//			log.info("[Cannibalism] JSON updated");
//	}
}
