import './App.css';
import React from 'react';


class MenuList extends React.Component
{
  render()
  {
    return (
      <div className="menu-list">
        <li>
          <button className="my-button" onClick={this.props.onNewGameClick}>New Game</button>
          <button className="my-button">About</button>
        </li>
      </div>
    );
  }
}

class AlphabetTable extends React.Component
{
  constructor()
  {
    super();

    this.generalStyle = "letter-button";
    this.hideStyle = "hide-element";

    const alphabet = [];

    let index = 0;
    for (let i = 65; i < 90; i += 3)
    {
      const subArray = [];
      
      subArray.push({letter: String.fromCharCode(i), classNames: this.generalStyle});
      subArray.push({letter: String.fromCharCode(i + 1), classNames: this.generalStyle});
      subArray.push({letter: String.fromCharCode(i + 2), classNames: this.generalStyle});
    
      alphabet[index++] = (subArray);
    }

    alphabet[alphabet.length - 1][2].classNames = this.hideStyle;

    this.state = {alphabet};
  }

  hideLetter = (row, column) =>
  {
    const {alphabet} = this.state;
    if (!alphabet[row][column].classNames.includes(this.hideStyle))
    {
      alphabet[row][column].classNames += " " + this.hideStyle;
    }
    this.setState({alphabet});
  }

  showAll = () =>
  {
    const {alphabet} = this.state;
    for (let i = 0; i < alphabet.length; i++)
    {
      for (let j = 0; j < 3; ++j) alphabet[i][j].classNames = this.generalStyle;
    }    
    this.setState({alphabet});
  }

  render()
  {
    return (
      <table className="alphabet-table">
      {this.state.alphabet.map((element, index) => (
        <tr key={index}>
          <td><button disabled={this.props.disabled && "disabled"} className={this.state.alphabet[index][0].classNames} onClick={ ()=>{this.props.onLetterClick(element[0].letter); this.hideLetter(index, 0);} }>{element[0].letter}</button></td>
          <td><button disabled={this.props.disabled && "disabled"} className={this.state.alphabet[index][1].classNames} onClick={ ()=>{this.props.onLetterClick(element[1].letter); this.hideLetter(index, 1);} }>{element[1].letter}</button></td>
          <td><button disabled={this.props.disabled && "disabled"} className={this.state.alphabet[index][2].classNames} onClick={ ()=>{this.props.onLetterClick(element[2].letter); this.hideLetter(index, 2);} }>{element[2].letter}</button></td>
        </tr>
      ))}
      </table>
    );
  }
}

class LetterCell extends React.Component
{
  render()
  {
    return (
      <div className="letter-cell">
        <label className="letter">{this.props.isHidden ? "" : this.props.letter}</label>              
        <div className="letter-underscore"></div>
      </div>
    );
  }
}

class HiddenWord extends React.Component
{
  static getDerivedStateFromProps(props, state)
  {    
    const letterCount = props.letterCount;
    const letterArray = [];
    for (let i = 0; i < letterCount; ++i)
    {
      letterArray.push({ letter: "", isHidden: true });
    }

    return {letterArray};
  }

  updateWord = (letter, positions) =>
  {
    const letterArray = this.state.letterArray;    

    for (let i = 0; i < positions.length; ++i)
    {
      letterArray[positions[i]].letter = letter;
      letterArray[positions[i]].isHidden = false;
    }

    this.setState({ letterArray });
  }

  render()
  {
    return (
      <div className="hidden-word">
      { this.state.letterArray.map((element) => 
      {
        return <LetterCell letter={element.letter} isHidden={element.isHidden} />;
      }) }
      </div>
    );
  }
}

class GameOver extends React.Component
{
  render()
  {
    return (
      <div className="game-over">
        <label>Game Over</label>
        <button onClick={()=>this.props.onRestartPressed()}>Restart</button>
      </div>
    );
  }
}

class Game extends React.Component
{
  constructor()
  {
    super();

    this.hiddenWord = React.createRef();
    this.alphabetTable = React.createRef();

    this.state = { wordHash: 0, letterCount: 1, imageName: 1, isGameOver: false };

    this.imageFolder = "/images/";
    this.imageExt = ".png";
    this.imageCount = 7;
  }

  componentDidMount()
  {
    this.fetchRandomWord();
  }

  fetchRandomWord = () =>
  {
    if (true)
    {
      fetch("/getRandomWord", { method: 'get' })
        .then(response => { if (!response.ok) throw "server error"; return response.json();}, error => alert("server error"))  
        .then(response => this.setState({ wordHash: response.wordHash, letterCount: response.letterCount}), error => alert("parse data error"));    
    }
  }

  getCurrentImageState = () =>
  {
    return window.location.origin + this.imageFolder + this.state.imageName + this.imageExt;
  }

  onRestartPressed = () =>
  {
    this.imageCount = 7;
    this.setState({isGameOver: false, imageName: 1});
    this.alphabetTable.current.showAll();
    this.fetchRandomWord();
  }

  checkGameOver = () =>
  {
    if (!--this.imageCount)
    {
      this.setState({isGameOver: true});
    }
  }

  hangUpMan = () =>
  {
    this.setState({imageName: this.state.imageName + 1});
    this.checkGameOver();
  }

  onLetterClick = (letter) =>
  {
    const body = { wordHash: this.state.wordHash, letter};
    if (false)
    {
      fetch("/getLetterPositions", 
      {
        method: 'post',
        body: JSON.stringify(body)
      })
        .then(response => { if (!response.ok) throw "server error"; return response.text();})  
        .then(response => 
          {
            if (response.length !== 0)
            {
              this.hiddenWord.current.updateWord(letter, response);
            }
            else
            {
              this.hangUpMan();
            }            
          })
        .catch(error => alert(error));    
    }

    this.hiddenWord.current.updateWord(letter, [0,1]);
    this.hangUpMan();
  }

  render()
  {
    return (
      <div className="game">
        <div className="first-part">
          <img className="img" alt={"hangman"} src={this.getCurrentImageState()} />
          <AlphabetTable ref={this.alphabetTable} disabled={this.state.isGameOver} onLetterClick={this.onLetterClick} />
        </div>
        <div className="second-part">
          <HiddenWord ref={this.hiddenWord} letterCount={this.state.letterCount} />             
        </div>
        {this.state.isGameOver && <GameOver onRestartPressed={this.onRestartPressed}/>}
      </div>
    );
  }
}

class App extends React.Component
{
  constructor()
  {
    super();

    this.onNewGameClick = this.onNewGameClick.bind(this);

    this.state = { 
                    activeComponent: "game",
                    components: { menu: <MenuList onNewGameClick={this.onNewGameClick}/>, game: <Game /> }
                 };
  }

  onNewGameClick()
  {
    this.setState({ activeComponent: "game" });
  }

  render()
  {
    return (
      <div className="App">
        { this.state.components[this.state.activeComponent] }
      </div>
    );
  }
}

export default App;
