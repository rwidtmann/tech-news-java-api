

async function upvoteClickHandler(event) {
  event.preventDefault();

  const id = window.location.toString().split('/')[
    window.location.toString().split('/').length - 1
  ];

//  const uId = document.querySelector('#user-id').value.trim();

  const response = await fetch('/api/posts/upvote', {
    method: 'PUT',
    body: JSON.stringify({
        postId: id
    }),
    headers: {
      'Content-Type': 'application/json'
    }
  });

  if (response.ok) {
    document.location.reload();
  } else {
    alert(response.statusText);
  }

}

document.querySelector('.upvote-btn').addEventListener('click', upvoteClickHandler);