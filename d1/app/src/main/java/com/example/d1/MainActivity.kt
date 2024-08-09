package com.example.d1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext

import com.android.volley.toolbox.ImageRequest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            UserProfileSection(
                userProfile = UserProfile(
                    profilePictureUrl = "https://example.com/profile_picture.jpg",
                    username = "john_doe",
                    bio = "Software engineer and cat lover",
                    isFollowing = true,
                    postCount = 100,
                    followerCount = 1000,
                    followingCount = 500
                )
            )
        }
    }
}
@Composable
fun UserProfileSection(
    userProfile: UserProfile,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Profile picture
        UserProfilePicture(
            imageUrl = userProfile.profilePictureUrl,
            size = 80.dp
        )

        // Username and bio
        Column(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = userProfile.username,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = userProfile.bio,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Follow button
        FollowButton(
            isFollowing = userProfile.isFollowing,
            onClick = { /* handle follow/unfollow action */ }
        )

        // Profile stats
        Row(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            ProfileStat(
                label = "Posts",
                value = userProfile.postCount.toString()
            )
            ProfileStat(
                label = "Followers",
                value = userProfile.followerCount.toString()
            )
            ProfileStat(
                label = "Following",
                value = userProfile.followingCount.toString()
            )
        }
    }
}

@Composable
private fun UserProfilePicture(
    imageUrl: String,
    size: Dp
) {
    Image(
        bitmap = rememberImageRequest(imageUrl).await().value,
        contentDescription = "Profile picture",
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .border(1.dp, Color.Gray, CircleShape)
    )
}

@Composable
private fun rememberImageRequest(url: String): coil.request.ImageRequest {
    val context = LocalContext.current
    return com.android.volley.toolbox.Builder(context)
        .data(url)
        .build()ImageRequest
}

@Composable
private fun FollowButton(
    isFollowing: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text(
            text = if (isFollowing) "Unfollow" else "Follow"
        )
    }
}

@Composable
private fun ProfileStat(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

data class UserProfile(
    val profilePictureUrl: String,
    val username: String,
    val bio: String,
    val isFollowing: Boolean,
    val postCount: Int,
    val followerCount: Int,
    val followingCount: Int
)