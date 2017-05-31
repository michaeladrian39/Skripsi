package view;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author michaeladrian39
 */
public class PuzzleFileFilter extends FileFilter
{

    @Override
    public boolean accept(File puzzleFile)
    {
        return puzzleFile.isDirectory() 
                || puzzleFile.getAbsolutePath().endsWith(".txt");
    }

    @Override
    public String getDescription()
    {
        return "Text documents (*.txt)";
    }
    
}
