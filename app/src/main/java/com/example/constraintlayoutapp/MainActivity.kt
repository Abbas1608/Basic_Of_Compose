package com.example.constraintlayoutapp

import com.example.constraintlayoutapp.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.HorizontalAlign
import com.example.constraintlayoutapp.ui.theme.ConstraintLayoutAppTheme
import kotlinx.coroutines.Delay
import java.nio.file.WatchEvent
import kotlin.time.Duration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyColumn {
                items(1){

                    //   1. ConstarintLayout()
//            animationButton()
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
//            {
//                CircularProgressBar(0.8f,100)
//            }
//          2.
//            Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
//            {
//                MultiColorText()
//            }

                    //Drop down
                    Box (modifier = Modifier.padding(top = 30.dp),
                        contentAlignment = Alignment.Center)
                    {
                        DropDown("Hello World ",
                            modifier = Modifier.fillMaxSize())
                        {
                            // here passing all after open drop then display element
                            Text(text =  " This is my first drop down ",
                                fontSize = 25.sp,
                                modifier = Modifier.fillMaxWidth()
                                    .height(100.dp)
                                    .background(Color.Green)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))

                    Box(
                        contentAlignment = Alignment.Center,

                        ) {
                        Navigation()
                    }
                }

            }


        }

    }

}

// constraint layout
//@Preview(showBackground = true)
@Composable
fun ConstarintLayout() {
    // constrain set is use to define constraint
    val constraints = ConstraintSet {
// reference for each compose constrain layout
        val greenBox = createRefFor("greenBox")
        val RedBox = createRefFor("RedBox")
// use the guide line  for better ui
        val guideline = createGuidelineFromTop(20.dp)
        constrain(greenBox) {
            top.linkTo(guideline)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(RedBox) {
            top.linkTo(parent.top)
            start.linkTo(greenBox.end)
            width = Dimension.value(100.dp)
            // for full remain size use :
            //width= Dimension.fillToConstraints same for height
            height = Dimension.value(100.dp)
        }
    }
    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize())
    {// connected layout with id
        Box(modifier = Modifier
            .layoutId("greenBox")
            .background(Color.Green))
        Box(modifier = Modifier
            .layoutId("RedBox")
            .background(Color.Red))

    }
}


// sample animation
//@Preview(showBackground = true)
@Composable
fun animationButton()
{       // creating a mutable state for size change on click on button
    var sizeState by remember {mutableStateOf(200.dp)  }
    val size by animateDpAsState(  // using animated dp state change
        targetValue = sizeState,
        // type of animation
        // type 1
//        tween(  // add functional for animation
//            delayMillis = 300,
//            durationMillis = 3000,
//            easing = LinearOutSlowInEasing
//        )
       // type 2
//        spring(
//            Spring.DampingRatioLowBouncy
//      )

    )
    //type 3 use for repeated animation mood
    val InfiniteTransition = rememberInfiniteTransition()
    val color by InfiniteTransition.animateColor(
        initialValue = Color.Green,
        targetValue = Color.Magenta,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 3000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.size(size).background(color), //color pass here for type 3
        contentAlignment = Alignment.Center)
    {
        Button(onClick =  {
            sizeState += 50.dp
        }

        ) {
            Text(text = "inceraing size")
        }

    }
}


//Animated  Circular Progress Bar
@Composable
fun CircularProgressBar(
    percentage: Float,   // for how much circle round
    number : Int,        // max number progress
    radius : Dp = 50.dp, // radius of circle
    strokewidth :Dp = 9.dp,   // stroke of circle
    fontsize : TextUnit = 30.sp, // font size of  number text
    color: Color = Color.Green, // color of circle
    animDuration: Int = 1000,  // animation duration
    animDelay: Int = 0         // animation delay
)
{
    var animationplayed by remember {
        mutableStateOf(false)  // use to inial not animated
    }
    val curpercentage = animateFloatAsState(
        // target if animationplayed is there then go to animation with max percentage value
        // else no animation value 0
        targetValue = if (animationplayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )
    LaunchedEffect(true) {
        // launched effect true that mean the animation started
        animationplayed= true
    }

    // main code to display

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius*2f))
    {
       androidx.compose.foundation.Canvas(modifier = Modifier.size(radius*2f))
       {
           drawArc(
               color = Color.Green,
               -90f, // starting angle
               360 * curpercentage.value , // ending angle
               useCenter = false, // false for can't make complete cricle
               style = Stroke(
                   strokewidth.toPx(),  // stroke size into piexl
                   cap = StrokeCap.Round  // for round shape
               )
           )
       }

        Text(text = (curpercentage.value * number).toInt().toString().toString(),
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

// Multi color texts
//@Preview
@Composable
fun MultiColorText()
{
    Text(text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 40.sp,
                color = Color.Green
            )
        )
        {
            append("A")
        }
        append("bbas")
        Spacer(modifier = Modifier.width(100.dp))
        withStyle(
            style = SpanStyle(
                fontSize = 40.sp,
                color = Color.Green
            )
        )
        {
            append("S")
        }
        append("haikh")
    },
        fontSize = 25.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold)
}

// Drop Down
@Composable
fun DropDown(
    text: String,
    modifier: Modifier = Modifier,
    initiallyOpened : Boolean = false,
    content: @Composable () -> Unit
)
{
    // drop down is open ?
    var isOpen by remember {
        mutableStateOf(initiallyOpened)
    }
    // manage animation
    val alpha = animateFloatAsState(
        // is drop down is open then use full size , else zero size
        targetValue =  if(isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300 // 3 sec
        )
    )

    // manage animation at x- axis
    val rotateX = animateFloatAsState(
        targetValue = if (isOpen) 0f else -90f,  // for swart animate flip
        animationSpec = tween(
            durationMillis = 300
        )
    )

    // Row inside column to arrage the text and icon of drop down
    Column(
        modifier = Modifier.fillMaxWidth()
    )
    {
        Row(
            modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween ,// for element in both ends
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.Black
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = " Open and close the drop down",
                tint = Color.Black,
                modifier = Modifier
                    .clickable{
                        // after clicking icon turn around ^
                        isOpen = !isOpen
                    }
                    .scale(1f,if(isOpen) -1f else 1f)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
                .graphicsLayer {
                    // its allow to 3d transformation
                    transformOrigin = TransformOrigin(0.5f,0f)
                    rotationX = rotateX.value // pass x axis rotation
                }
                .alpha(alpha.value)
        ) {
            content()
        }
    }

}

//@Preview(showBackground = true)
@Composable
fun cardBox(image: Painter, text: String)
{
        Card(
            modifier = Modifier.height(180.dp)
                .width(180.dp)
                .padding(
                    start = 25.dp,
                    top = 20.dp)
        ) {
            Image(
                painter = image, // Directly using the image parameter
                contentDescription = " ",
                modifier = Modifier.size(100.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

                Text(text = text, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    , fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

        }
    }

@Preview(showBackground = true)
@Composable
fun mainList() {
    val image1 = painterResource(R.drawable.cylinder)
    val image2 = painterResource(R.drawable.cube)
    val image3 = painterResource(R.drawable.sphere)
    val image4 = painterResource(R.drawable.triangular_prism)


    Spacer(modifier = Modifier.height(40.dp).fillMaxWidth())
    

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item{
//1
            Row {
                cardBox(image1,"dcijn")
                cardBox(image2,"dcijn")
            }
            Row {
                cardBox(image3,"dcijn")
                cardBox(image4,"dcijn")
            }
//2
            Row {
                cardBox(image1,"dcijn")
                cardBox(image2,"dcijn")
            }
            Row {
                cardBox(image3,"dcijn")
                cardBox(image4,"dcijn")
            }
//3
            Row {
                cardBox(image1,"dcijn")
                cardBox(image2,"dcijn")
            }
            Row {
                cardBox(image3,"dcijn")
                cardBox(image4,"dcijn")
            }
//4
            Row {
                cardBox(image1,"dcijn")
                cardBox(image2,"dcijn")
            }
            Row {
                cardBox(image3,"dcijn")
                cardBox(image4,"dcijn")
            }
//5
            Row {
                cardBox(image1,"dcijn")
                cardBox(image2,"dcijn")
            }
            Row {
                cardBox(image3,"dcijn")
                cardBox(image4,"dcijn")
            }
        }
    }
}





