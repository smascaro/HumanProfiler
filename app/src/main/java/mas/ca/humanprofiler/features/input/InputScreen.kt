package mas.ca.humanprofiler.features.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mas.ca.humanprofiler.R
import mas.ca.humanprofiler.di.UiLayerInjection
import mas.ca.humanprofiler.domain.entities.Age
import mas.ca.humanprofiler.domain.entities.Name
import mas.ca.humanprofiler.domain.use_cases.GetProfileUseCase
import mas.ca.humanprofiler.ui.ThemedText
import mas.ca.humanprofiler.ui.ThemedTextOptions
import mas.ca.humanprofiler.ui.theme.DevicePreviews
import mas.ca.humanprofiler.ui.theme.HumanProfilerTheme
import mas.ca.humanprofiler.ui.theme.NameInputWidth
import mas.ca.humanprofiler.ui.theme.PaddingLarge

data class InputScreenState(
    val name: Name? = null,
    val guessedAge: Age? = null,
    val showLoadingIndicator: Boolean = false,
    val error: GetProfileUseCase.ErrorType? = null
)

@Composable
fun InputScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = viewModel<InputViewModel>(factory = UiLayerInjection.INSTANCE!!.provideInputViewModelFactory())
    val state by viewModel.uiState.collectAsState(InputScreenState())
    InputScreen(
        modifier = modifier,
        state = state,
        onNameSubmit = { name -> viewModel.onNameSubmit(name) }
    )
}

@Composable
private fun InputScreen(
    modifier: Modifier = Modifier,
    state: InputScreenState = InputScreenState(),
    onNameSubmit: (Name) -> Unit = {}
) {
    val isLocalInspectionMode = LocalInspectionMode.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(PaddingLarge),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var userInput by remember {
            if (isLocalInspectionMode) mutableStateOf("John Doe")
            else mutableStateOf("")
        }
        TextField(
            modifier = Modifier.widthIn(max = NameInputWidth),
            value = userInput,
            placeholder = { Text(text = stringResource(R.string.enter_a_name)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onDone = { onNameSubmit(Name(userInput)) }),
            onValueChange = { userInput = it },

            )
        SubmitButton(
            modifier = Modifier
                .widthIn(max = NameInputWidth)
                .padding(top = PaddingLarge),
            state = state,
            userInput = userInput,
            onNameSubmit = onNameSubmit
        )

        if (state.showLoadingIndicator) {
            LoadingIndicator(
                modifier = Modifier.padding(top = PaddingLarge)
            )
        }

        if (state.error != null) {
            val error = when (state.error) {
                GetProfileUseCase.ErrorType.NO_INTERNET -> stringResource(R.string.no_internet)
                GetProfileUseCase.ErrorType.INVALID_NAME -> stringResource(R.string.invalid_name)
                GetProfileUseCase.ErrorType.UNKNOWN -> stringResource(R.string.something_went_wrong_please_try_again)
            }
            ThemedText(
                modifier = Modifier.padding(top = PaddingLarge),
                text = error,
                options = ThemedTextOptions(
                    textAlignment = TextAlign.Center,
                    style = ThemedText.Style.ERROR
                )
            )
        }

        if (state.guessedAge != null) {
            ThemedText(
                modifier = Modifier.padding(top = PaddingLarge),
                annotatedText = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W100)) {
                        append(stringResource(R.string.age_colon))
                    }
                    append(state.guessedAge.value.toString())
                },
                options = ThemedTextOptions(
                    type = ThemedText.Type.HEADLINE_MEDIUM
                )
            )
        }
    }
}

@Composable
private fun SubmitButton(
    modifier: Modifier = Modifier,
    state: InputScreenState,
    userInput: String,
    onNameSubmit: (Name) -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        enabled = !state.showLoadingIndicator && userInput.isNotBlank(),
        shape = RoundedCornerShape(4.dp),
        onClick = { onNameSubmit(Name(userInput)) },

        ) {
        ThemedText(
            text = stringResource(R.string.submit),
        )
    }
}

@Composable
private fun LoadingIndicator(
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
        ThemedText(
            modifier = Modifier.padding(start = PaddingLarge),
            text = stringResource(R.string.guessing)
        )
    }
}

@DevicePreviews
@Composable
private fun InputScreenPreview() {
    HumanProfilerTheme {
        InputScreen(
            modifier = Modifier,
            state = InputScreenState(),
            onNameSubmit = {}
        )
    }
}