package me.pulse.parser.boilerpipe;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.http.*;

import sun.util.logging.resources.logging;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.HTMLHighlighter;

@SuppressWarnings("serial")
public class GAEBoilerpipeServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(GAEBoilerpipeServlet.class.getName());
	
	final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
//	final BoilerpipeExtractor extractor = CommonExtractors.DEFAULT_EXTRACTOR;
//	final BoilerpipeExtractor extractor = CommonExtractors.CANOLA_EXTRACTOR;
//	final BoilerpipeExtractor extractor = CommonExtractors.LARGEST_CONTENT_EXTRACTOR;
	
	final boolean includeImages = true;
	final boolean bodyOnly = false;
	final HTMLHighlighter hh = HTMLHighlighter.newExtractingInstance(includeImages, bodyOnly);
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		try
		{
			URL url = new URL(req.getParameter("url"));
			
			String extractedHtml = hh.process(url, extractor);
			
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<base href=\"" + url + "\" >");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text-html; charset=utf-8\" />");
			out.println(extractedHtml);

			out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
