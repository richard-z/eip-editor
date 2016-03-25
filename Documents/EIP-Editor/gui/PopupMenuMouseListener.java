package gui;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

public class PopupMenuMouseListener extends MouseAdapter
{
  private final JPopupMenu popmen;

  public PopupMenuMouseListener( JPopupMenu popmen )
  {
    this.popmen = popmen;
  }

  @Override public void mouseReleased( MouseEvent me ) {
    if ( me.isPopupTrigger() )
      popmen.show( me.getComponent(), me.getX(), me.getY() );
  }
}