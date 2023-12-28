package com.example.pdfmakingincompose

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.pdfmakingincompose.ui.theme.PDFMakingInComposeTheme
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PDFMakingInComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                   Button(onClick = {
                       createPdf(context)
                   }) {
                       Text("Generate PDF")
                   }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun createPdf(
    context: Context
)
{

    val pageHeight = 1698
    val pageWidth = 1200


    val indiaPostImage =
        BitmapFactory.decodeResource(context.resources, R.drawable.indiapostpaymentsbankwhite)
    val bbpsImage = BitmapFactory.decodeResource(context.resources, R.drawable.bbps_assured)

    val pdfDocument = PdfDocument()
    val imagePaint = Paint()

    val header1Paint = Paint()
    val header2Paint = Paint()
    val textPaint = Paint()

    val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val myPage = pdfDocument.startPage(myPageInfo)

    //it is used for design pdf
    val canvas = myPage.canvas

    header1Paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    header2Paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

    header1Paint.textSize = 18f
    header2Paint.textSize = 13f
    textPaint.textSize = 13f

    header1Paint.color = ContextCompat.getColor(context, R.color.black)
    header2Paint.color = ContextCompat.getColor(context, R.color.black)
    textPaint.color = ContextCompat.getColor(context, R.color.black)

    val startPadding = 120f

    //region start pdf

    canvas.drawBitmap(
        Bitmap.createScaledBitmap(indiaPostImage, 280, 120, false),
        startPadding,
        120f,
        imagePaint
    )
    canvas.drawBitmap(Bitmap.createScaledBitmap(bbpsImage, 150, 150, false), pageWidth-270f, 120f, imagePaint)

    canvas.drawText("Payment Receipt", startPadding, 320f, header1Paint)

    canvas.drawText(
        "Receipt Date: ${LocalDate.now().toString().split(" ")[0]}",
        startPadding,
        340f,
        textPaint
    )

    canvas.drawText("Service Provider", startPadding, 370f, header2Paint)
    canvas.drawText("National Payment corporation of India (BBPS Dept.)", startPadding, 390f, textPaint)
    canvas.drawText("1001A, The Capital B Wing, 10th Floor, Bandra Kurla", startPadding, 410f, textPaint)
    canvas.drawText("Complex, Bandra (E), Mumbai", startPadding, 430f, textPaint)


    canvas.drawText("Payer", startPadding+500f, 370f, header2Paint)
   /* canvas.drawText(payBillResponseDto.dataObj?.customerName?:"", startPadding+500f, 390f, textPaint)
    canvas.drawText(payBillRequestDto.customerDetails?.mobileNo?:"", startPadding+500f, 410f, textPaint)
    canvas.drawText(payBillRequestDto.customerDetails?.email?:"", startPadding+500f, 430f, textPaint)
*/

    /*canvas.drawText("BBPS Biller Id : ${payBillRequestDto.billerDetails?.billerId?:"NA"}", startPadding, 460f, textPaint)
    canvas.drawText("BBPS Transaction Id : ${payBillResponseDto.txnId?:"NA"}", startPadding, 480f, textPaint)
    canvas.drawText("Payment Mode : ${payBillRequestDto.billerDetails?.billerName ?: "NA"}", startPadding, 500f, textPaint)
    canvas.drawText("Payment Channel : ${payBillRequestDto.channelDetails?.platform?:"NA"}", startPadding, 520f, textPaint)
    canvas.drawText("Approval Ref No : ${payBillResponseDto.dataObj?.approvalRefNum?:"NA"}", startPadding, 540f, textPaint)
*/
    val linePaint = Paint()

    canvas.drawLine(startPadding, 580f, pageWidth-startPadding, 580f, linePaint)
    canvas.drawLine(startPadding, 610f, pageWidth-startPadding, 610f, linePaint)
    canvas.drawLine(startPadding, 640f, pageWidth-startPadding, 640f, linePaint)
    canvas.drawLine(startPadding, 670f, pageWidth-startPadding, 670f, linePaint)
    canvas.drawLine(startPadding, 700f, pageWidth-startPadding, 700f, linePaint)

    canvas.drawLine(startPadding, 580f, startPadding, 700f, linePaint)
    canvas.drawLine(startPadding+700f, 580f, startPadding+700f, 700f, linePaint)
    canvas.drawLine(pageWidth-startPadding, 580f, pageWidth-startPadding, 700f, linePaint)


    canvas.drawText("Description", startPadding+10f, 600f, textPaint)
    canvas.drawText("Bill Amount", startPadding+10f+700f, 600f, textPaint)

   /* canvas.drawText("${payBillRequestDto.billerDetails?.billerName ?: ""} Base Amount", startPadding+10f, 630f, textPaint)
    canvas.drawText("Rs. ${payBillResponseDto.dataObj?.billAmount}", startPadding+10f+700f, 630f, textPaint)
*/
    canvas.drawText("Convince fee", startPadding+10f, 660f, textPaint)
//    canvas.drawText("Rs. $ccfValue", startPadding+10f+700f, 660f, textPaint)

/*
    val totalAmount = try{(payBillResponseDto.dataObj?.billAmount?:"00").toFloat() + ccfValue.toFloat() }catch (e:Exception){"00".toFloat()}
*/

    canvas.drawText("Total Amount", startPadding+10f, 690f, textPaint)
  /*  canvas.drawText("Rs. $totalAmount ", startPadding+10f+700f, 690f, textPaint)


    canvas.drawText("Total Amount in Words: ${convertToWords(totalAmount.toDouble())}", startPadding, 730f, header2Paint)

*/    canvas.drawText("Please Note:", startPadding, 760f, header2Paint)
    canvas.drawText("Dear Consumer, the bill payment will reflect in next 48 hours or in the next billing cycle, at your service provider's end. please contact IPPB customer support for any queries"
        , startPadding, 780f, textPaint)

    canvas.drawText("DECLARATION:", startPadding, 830f, header2Paint)
    canvas.drawText("This is not an invoice but only a confirmation of the amount paid against for the service as described above.", startPadding, 850f, textPaint)

// Calculate text width
    val text = "(This is computer generated receipt and does not require physical signature.)"
    val textWidth = textPaint.measureText(text)

// Calculate starting x-coordinate for centering
    val startX = (pageWidth - textWidth) / 2

// Draw the text centered at the bottom
    canvas.drawText(text, startX, pageHeight - startPadding, textPaint)





    pdfDocument.finishPage(myPage)
    //end region

    // below line is used to set the name of PDF file and its path.
    var save: String
    var num = 0
    var folder = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString() + "/Bbps_Report.pdf"
    )
    while (folder.exists()) {
        save = "Bbps_Report_" + num++ + ".pdf"
        folder = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), save
        )
    }
    try {
        folder.createNewFile()
        pdfDocument.writeTo(FileOutputStream(folder))
        Toast.makeText(context, "PDF file generated successfully.", Toast.LENGTH_SHORT).show()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    pdfDocument.close()
    val path = FileProvider.getUriForFile(  // change the package name
        context.applicationContext, context.applicationContext.packageName + ".provider", folder
    )
    val pdfOpenIntent = Intent(Intent.ACTION_VIEW)
    pdfOpenIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    pdfOpenIntent.setDataAndType(path, "application/pdf")
    try {
        context.startActivity(pdfOpenIntent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }

}

fun convertToWords(number: Double): String {
    try {
        val units = arrayOf("", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine")
        val teens = arrayOf("", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen")
        val tens = arrayOf("", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety")

        fun convertLessThanOneThousand(num: Int): String {
            return when {
                num == 0 -> ""
                num < 10 -> units[num]
                num < 20 -> teens[num - 10]
                num < 100 -> "${tens[num / 10]} ${convertLessThanOneThousand(num % 10)}"
                else -> "${units[num / 100]} Hundred ${convertLessThanOneThousand(num % 100)}"
            }
        }

        val indianNumberSystem = arrayOf("", "Thousand", "Lakh", "Crore")

        fun convertToIndianRupees(num: Int, level: Int): String {
            return when {
                num == 0 -> ""
                num < 1000 -> "${convertLessThanOneThousand(num)} ${indianNumberSystem[level]}"
                else -> "${convertLessThanOneThousand(num / 1000)} ${indianNumberSystem[level]} ${convertToIndianRupees(num % 1000, level + 1)}"
            }
        }

        if (number == 0.0) {
            return "Zero Rupees"
        }

        val parts = number.toString().split(".")
        val rupees = parts[0].toInt()
        val paise = if (parts.size > 1) parts[1].toInt() else 0

        val rupeesInWords = convertToIndianRupees(rupees, 0)
        val paiseInWords = if (paise > 0) " and ${convertLessThanOneThousand(paise)} Paise" else ""

        return "$rupeesInWords Rupees$paiseInWords"

    } catch (e:Exception){
        return "Check Report"
    }
}
